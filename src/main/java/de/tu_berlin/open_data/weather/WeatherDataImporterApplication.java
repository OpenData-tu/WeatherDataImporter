package de.tu_berlin.open_data.weather;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.cloud.task.configuration.EnableTask;

@SpringBootApplication
@EnableBatchProcessing
@EnableTask
public class WeatherDataImporterApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(WeatherDataImporterApplication.class, args);

    }
}
