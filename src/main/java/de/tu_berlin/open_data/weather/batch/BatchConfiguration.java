package de.tu_berlin.open_data.weather.batch;

import de.tu_berlin.open_data.weather.http.HttpFileDownloaderService;
import de.tu_berlin.open_data.weather.model.DHTSensor;
import de.tu_berlin.open_data.weather.model.SDSSensor;
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
    private String resourceUrl = "http://archive.luftdaten.info/2017-06-18/";


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
        String sensorType = "bme";
        multiResourceItemReader.setResources(httpFileDownloaderService.downloadFromUrl(resourceUrl, sensorType));

        FlatFileItemReader reader = new FlatFileItemReader<>();

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
    public MultiResourceItemReader readerDHT() throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        MultiResourceItemReader multiResourceItemReader = new MultiResourceItemReader();
        String sensorType = "dht";
        multiResourceItemReader.setResources(httpFileDownloaderService.downloadFromUrl(resourceUrl, sensorType));

        FlatFileItemReader reader = new FlatFileItemReader<>();

        reader.setLinesToSkip(1);

        Schema userModelInstance = new DHTSensor();


        reader.setLineMapper(new DefaultLineMapper<Schema>() {{
            setLineTokenizer(new DelimitedLineTokenizer(userModelInstance.getDelimiter()) {{
                setNames(applicationService.getFields(DHTSensor.class));
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Schema>() {{
                setTargetType(userModelInstance.getClass());
            }});
        }});

        multiResourceItemReader.setDelegate(reader);
        return multiResourceItemReader;
    }


    @Bean
    public MultiResourceItemReader readerSDS() throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        MultiResourceItemReader multiResourceItemReader = new MultiResourceItemReader();
        String sensorType = "sds";
        multiResourceItemReader.setResources(httpFileDownloaderService.downloadFromUrl(resourceUrl, sensorType));

        FlatFileItemReader reader = new FlatFileItemReader<>();

        reader.setLinesToSkip(1);

        Schema userModelInstance = new SDSSensor();


        reader.setLineMapper(new DefaultLineMapper<Schema>() {{
            setLineTokenizer(new DelimitedLineTokenizer(userModelInstance.getDelimiter()) {{
                setNames(applicationService.getFields(SDSSensor.class));
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
    public DHTSensorItemProcessor dhtSensorItemProcessor(){
        return new DHTSensorItemProcessor();
    }

    @Bean
    public SDSSensorItemProcessor sdsSensorItemProcessor(){
        return new SDSSensorItemProcessor();
    }

    @Bean
    public JsonItemWriter writer() {
        return new JsonItemWriter();
    }

    @Bean
    public DHTSensorJsonItemWriter dhtSensorJsonItemWriter(){
        return new DHTSensorJsonItemWriter();
    }

    @Bean
    public SDSSensorJsonItemWriter sdsSensorJsonItemWriter(){
        return new SDSSensorJsonItemWriter();
    }

    @Bean
    public Job weatherDataJob(JobCompletionNotificationListener listener) throws NoSuchMethodException, IllegalAccessException, InstantiationException, ClassNotFoundException, InvocationTargetException, MalformedURLException {
        return jobBuilderFactory.get("weatherDataJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step3()).next(step2()).next(step1())
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
                .reader(readerDHT())
                .processor(dhtSensorItemProcessor())
                .writer(dhtSensorJsonItemWriter())
                .build();
    }

    @Bean
    public Step step3() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException, MalformedURLException, ClassNotFoundException {
        return stepBuilderFactory.get("step3")
                .<WeatherData, WeatherData>chunk(10)
                .reader(readerSDS())
                .processor(sdsSensorItemProcessor())
                .writer(sdsSensorJsonItemWriter())
                .build();
    }


    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcess(JobRegistry jobRegistry) {
        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
        return jobRegistryBeanPostProcessor;
    }
}
