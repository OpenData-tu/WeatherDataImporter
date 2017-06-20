package de.tu_berlin.open_data.weather.batch;

import de.tu_berlin.open_data.weather.http.HttpFileDownloaderService;
import de.tu_berlin.open_data.weather.model.Schema;
import de.tu_berlin.open_data.weather.model.WeatherData;
import de.tu_berlin.open_data.weather.service.ApplicationService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;


/**
 * Created by ahmadjawid on 5/21/17.
 */

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
//    @Value("${resource.url}")
//    private String resourceUrl;


    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public ApplicationService applicationService;

    @Autowired
    public DataSource dataSource;

    @Autowired
    private HttpFileDownloaderService httpFileDownloaderService;

    @Bean
    public MultiResourceItemReader readerBME() throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        MultiResourceItemReader multiResourceItemReader = new MultiResourceItemReader();
        // readerBME.setResource(new ClassPathResource("new_data.csv"));
//        URL url1 = new URL("http://archive.luftdaten.info/2017-06-18/2017-06-18_bme280_sensor_548.csv");
//        URL url2 = new URL("http://archive.luftdaten.info/2017-06-18/2017-06-18_bme280_sensor_141.csv");
//
//        UrlResource[] urlResource = {};
//
//        List<UrlResource> resources = new ArrayList<>();
//        resources.add(new UrlResource(url1));
//        resources.add(new UrlResource(url2));
        multiResourceItemReader.setResources(httpFileDownloaderService.downloadFromUrl("http://archive.luftdaten.info/2017-06-18/"));

//        urlResource = resources.toArray(urlResource);
//
//        System.out.println(urlResource.length);

        FlatFileItemReader reader = new FlatFileItemReader<>();


        //readerBME.setResource(new UrlResource(url));

        reader.setLinesToSkip(1);

        Schema userModelInstance = new WeatherData();


        reader.setLineMapper(new DefaultLineMapper<Schema>() {{
            setLineTokenizer(new DelimitedLineTokenizer(userModelInstance.getDelimiter()) {{
                setNames(applicationService.getFields(WeatherData.class));
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Schema>() {{
                setTargetType(userModelInstance.getClass());
            }});
        }});

        multiResourceItemReader.setDelegate(reader);
        return multiResourceItemReader;
    }

    @Bean
    public WeatherDataItemProcessor processor() {
        return new WeatherDataItemProcessor();
    }

    @Bean
    public JsonItemWriter writer() {
        return new JsonItemWriter();
    }

    @Bean
    public Job weatherDataJob(JobCompletionNotificationListener listener) throws NoSuchMethodException, IllegalAccessException, InstantiationException, ClassNotFoundException, InvocationTargetException, MalformedURLException {
        return jobBuilderFactory.get("weatherDataJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException, MalformedURLException, ClassNotFoundException {
        return stepBuilderFactory.get("step1")
                .<WeatherData, WeatherData>chunk(10)
                .reader(readerBME())
                .processor(processor())
                .writer(writer())
                .build();
    }


    @Bean
    public Step step2() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException, MalformedURLException, ClassNotFoundException {
        return stepBuilderFactory.get("step2")
                .<WeatherData, WeatherData>chunk(10)
                .reader(readerBME())
                .processor(processor())
                .writer(writer())
                .build();
    }


    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcess(JobRegistry jobRegistry) {
        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
        return jobRegistryBeanPostProcessor;
    }
}
