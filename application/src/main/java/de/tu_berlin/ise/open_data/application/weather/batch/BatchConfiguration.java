package de.tu_berlin.ise.open_data.application.weather.batch;

import de.tu_berlin.ise.open_data.application.weather.config.ResourceProperties;
import de.tu_berlin.ise.open_data.application.weather.http.HttpService;
import de.tu_berlin.ise.open_data.application.weather.model.BMESensor;
import de.tu_berlin.ise.open_data.application.weather.model.DHTSensor;
import de.tu_berlin.ise.open_data.library.batch.StepProcessListener;
import de.tu_berlin.ise.open_data.library.batch.JobCompletionNotificationListener;
import de.tu_berlin.ise.open_data.library.batch.JsonItemWriter;
import de.tu_berlin.ise.open_data.library.service.ApplicationService;
import de.tu_berlin.ise.open_data.application.weather.model.SDSAndPPDSensor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;


/**
 * Created by ahmadjawid on 5/21/17.
 */

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    private ResourceProperties resourceProperties;


    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public ApplicationService applicationService;

    @Autowired
    private HttpService httpFileDownloaderService;

    @Bean
    public MultiResourceItemReader readerBME() throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        CustomMultiResourceItemReader multiResourceItemReader = new CustomMultiResourceItemReader();

        multiResourceItemReader.setProperties(httpFileDownloaderService, applicationService, resourceProperties.getUrl(), "bme", BMESensor.class);

        return multiResourceItemReader;
    }


    @Bean
    public MultiResourceItemReader readerDHT() throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        CustomMultiResourceItemReader multiResourceItemReader = new CustomMultiResourceItemReader();

        multiResourceItemReader.setProperties(httpFileDownloaderService, applicationService, resourceProperties.getUrl(), "dht", DHTSensor.class);

        return multiResourceItemReader;
    }


    @Bean
    public MultiResourceItemReader readerSDS() throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        CustomMultiResourceItemReader multiResourceItemReader = new CustomMultiResourceItemReader();

        multiResourceItemReader.setProperties(httpFileDownloaderService, applicationService, resourceProperties.getUrl(), "sds", SDSAndPPDSensor.class);

        return multiResourceItemReader;
    }


    @Bean
    public MultiResourceItemReader readerPPD() throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        CustomMultiResourceItemReader multiResourceItemReader = new CustomMultiResourceItemReader();

        multiResourceItemReader.setProperties(httpFileDownloaderService, applicationService, resourceProperties.getUrl(), "ppd", SDSAndPPDSensor.class);

        return multiResourceItemReader;
    }


    @Bean
    public BMESensorItemProcessor bmeSensorItemProcessor() {
        return new BMESensorItemProcessor();
    }

    @Bean
    public DHTSensorItemProcessor dhtSensorItemProcessor() {
        return new DHTSensorItemProcessor();
    }

    @Bean
    public SDSAndPPDSensorItemProcessor sdsAndPPDSensorItemProcessor() {
        return new SDSAndPPDSensorItemProcessor();
    }

    @Bean
    public JsonItemWriter writer() {
        return new JsonItemWriter();
    }

    @Bean
    public StepProcessListener stepExecutionListener() {
        return new StepProcessListener();
    }

    @Bean
    public Job weatherDataJob(JobCompletionNotificationListener listener) throws NoSuchMethodException, IllegalAccessException, InstantiationException, ClassNotFoundException, InvocationTargetException, MalformedURLException {
        return jobBuilderFactory.get("weatherDataJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1()).next(step2()).next(step3()).next(step4())
                .end()
                .build();
    }

    @Bean
    public Step step1() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException, MalformedURLException, ClassNotFoundException {
        return stepBuilderFactory.get("step1").listener(stepExecutionListener())
                .<BMESensor, BMESensor>chunk(10000)
                .reader(readerBME())
                .processor(bmeSensorItemProcessor())
                .writer(writer())
                .build();
    }


    @Bean
    public Step step2() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException, MalformedURLException, ClassNotFoundException {
        return stepBuilderFactory.get("step2").listener(stepExecutionListener())
                .<BMESensor, BMESensor>chunk(10000)
                .reader(readerDHT())
                .processor(dhtSensorItemProcessor())
                .writer(writer())
                .build();
    }

    @Bean
    public Step step3() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException, MalformedURLException, ClassNotFoundException {
        return stepBuilderFactory.get("step3").listener(stepExecutionListener())
                .<BMESensor, BMESensor>chunk(10000)
                .reader(readerSDS())
                .processor(sdsAndPPDSensorItemProcessor())
                .writer(writer())
                .build();
    }

    @Bean
    public Step step4() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException, MalformedURLException, ClassNotFoundException {
        return stepBuilderFactory.get("step4").listener(stepExecutionListener())
                .<BMESensor, BMESensor>chunk(10000)
                .reader(readerPPD())
                .processor(sdsAndPPDSensorItemProcessor())
                .writer(writer())
                .build();
    }
}
