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
 * Configurations including jobs, job steps and how to read, write and process
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


    /**
     * Register a bean of type {@link MultiResourceItemReader} which defines how to read data from the source (all files
     * which are of type BME sensor type
     * @return CustomMultiResourceItemReader
     * */
    @Bean
    public MultiResourceItemReader readerBME() throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        CustomMultiResourceItemReader multiResourceItemReader = new CustomMultiResourceItemReader();
        //Set the properties
        multiResourceItemReader.setProperties(httpFileDownloaderService, applicationService, resourceProperties.getUrl(), "bme", BMESensor.class);

        return multiResourceItemReader;
    }

    /**
     * Register a bean of type {@link MultiResourceItemReader} which defines how to read data from the source (all files
     * which are of type DHT sensor type
     * @return CustomMultiResourceItemReader
     * */
    @Bean
    public MultiResourceItemReader readerDHT() throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        CustomMultiResourceItemReader multiResourceItemReader = new CustomMultiResourceItemReader();
        //Set the properties
        multiResourceItemReader.setProperties(httpFileDownloaderService, applicationService, resourceProperties.getUrl(), "dht", DHTSensor.class);

        return multiResourceItemReader;
    }

    /**
     * Register a bean of type {@link MultiResourceItemReader} which defines how to read data from the source (all files
     * which are of type SDS sensor type
     * @return CustomMultiResourceItemReader
     * */
    @Bean
    public MultiResourceItemReader readerSDS() throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        CustomMultiResourceItemReader multiResourceItemReader = new CustomMultiResourceItemReader();

        //Set properties of the reader
        multiResourceItemReader.setProperties(httpFileDownloaderService, applicationService, resourceProperties.getUrl(), "sds", SDSAndPPDSensor.class);

        return multiResourceItemReader;
    }


    /**
     * Register a bean of type {@link MultiResourceItemReader} which defines how to read data from the source (all files
     * which are of type PPD sensor type
     * @return CustomMultiResourceItemReader
     * */
    @Bean
    public MultiResourceItemReader readerPPD() throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        CustomMultiResourceItemReader multiResourceItemReader = new CustomMultiResourceItemReader();

        multiResourceItemReader.setProperties(httpFileDownloaderService, applicationService, resourceProperties.getUrl(), "ppd", SDSAndPPDSensor.class);

        return multiResourceItemReader;
    }


    /**
     * Register a bean of {@link org.springframework.batch.item.ItemProcessor} which defines how to process individual
     * objects of BME sensor type
     * @return BMESensorItemProcessor
     * */
    @Bean
    public BMESensorItemProcessor bmeSensorItemProcessor() {
        return new BMESensorItemProcessor();
    }


    /**
     * Register a bean of {@link org.springframework.batch.item.ItemProcessor} which defines how to process individual
     * objects of DHT sensor type
     * @return DHTSensorItemProcessor
     * */
    @Bean
    public DHTSensorItemProcessor dhtSensorItemProcessor() {
        return new DHTSensorItemProcessor();
    }


    /**
     * Register a bean of {@link org.springframework.batch.item.ItemProcessor} which defines how to process individual
     * objects of SDS and PPD sensor type
     * @return SDSAndPPDSensorItemProcessor
     * */
    @Bean
    public SDSAndPPDSensorItemProcessor sdsAndPPDSensorItemProcessor() {
        return new SDSAndPPDSensorItemProcessor();
    }

    /**
     * Register a bean of {@link org.springframework.batch.item.ItemWriter} which defines how to write individual json objects to kafka queue
     * @return JsonItemWriter
     * */
    @Bean
    public JsonItemWriter writer() {
        return new JsonItemWriter();
    }


    /**
     * Register a bean of {@link org.springframework.batch.core.StepExecutionListener} which defines
     * methods for listening to the events of processing steps and chunks
     * @return StepProcessListener
     * */
    @Bean
    public StepProcessListener stepExecutionListener() {
        return new StepProcessListener();
    }


    /**
     * Registers a job named 'weatherDataJob' that is finished in four steps
     * @param listener
     * @return {@link Job}
     * */
    @Bean
    public Job weatherDataJob(JobCompletionNotificationListener listener) throws NoSuchMethodException, IllegalAccessException, InstantiationException, ClassNotFoundException, InvocationTargetException, MalformedURLException {
        return jobBuilderFactory.get("weatherDataJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1()).next(step2()).next(step3()).next(step4())
                .end()
                .build();
    }

    /**
     * Registers a job step named 'step1' which defines how to read, process and write.
     * Used to read, process, write BME sensor type objects.
     * @return {@link Job}
     * */
    @Bean
    public Step step1() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException, MalformedURLException, ClassNotFoundException {
        return stepBuilderFactory.get("step1").listener(stepExecutionListener())
                .<BMESensor, String>chunk(10000)
                .reader(readerBME())
                .processor(bmeSensorItemProcessor())
                .writer(writer())
                .build();
    }


    /**
     * Registers a job step named 'step2' which defines how to read, process and write.
     * Used to read, process, write DHT sensor type objects.
     * @return {@link Job}
     * */
    @Bean
    public Step step2() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException, MalformedURLException, ClassNotFoundException {
        return stepBuilderFactory.get("step2").listener(stepExecutionListener())
                .<BMESensor, String>chunk(10000)
                .reader(readerDHT())
                .processor(dhtSensorItemProcessor())
                .writer(writer())
                .build();
    }

    /**
     * Registers a job step named 'step3' which defines how to read, process and write.
     * Used to read, process, write SDS sensor type objects.
     * @return {@link Job}
     * */
    @Bean
    public Step step3() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException, MalformedURLException, ClassNotFoundException {
        return stepBuilderFactory.get("step3").listener(stepExecutionListener())
                .<BMESensor, String>chunk(10000)
                .reader(readerSDS())
                .processor(sdsAndPPDSensorItemProcessor())
                .writer(writer())
                .build();
    }

    /**
     * Registers a job step named 'step4' which defines how to read, process and write.
     * Used to read, process, write PPD sensor type objects.
     * @return {@link Job}
     * */
    @Bean
    public Step step4() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException, MalformedURLException, ClassNotFoundException {
        return stepBuilderFactory.get("step4").listener(stepExecutionListener())
                .<BMESensor, String>chunk(10000)
                .reader(readerPPD())
                .processor(sdsAndPPDSensorItemProcessor())
                .writer(writer())
                .build();
    }
}
