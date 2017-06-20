package de.tu_berlin.open_data.weather;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;

@SpringBootApplication
@EnableTask
public class WeatherDataImporterApplication {


    public static void main(String[] args) throws Exception {
        SpringApplication.run(WeatherDataImporterApplication.class, args);

    }
}
