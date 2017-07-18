package de.tu_berlin.open_data.weather;


import de.tu_berlin.ise.open_data.config.ServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;


@SpringBootApplication
@EnableTask
@Import(ServiceConfiguration.class)
public class WeatherDataImporterApplication implements CommandLineRunner {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(WeatherDataImporterApplication.class, args);


    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... strings) throws Exception {

//        jdbcTemplate.execute("UPDATE BATCH_JOB_EXECUTION a SET a.VERSION=a.VERSION + 1, END_TIME=LAST_UPDATED," +
//                " STATUS='FAILED', EXIT_CODE='FAILED' WHERE STATUS='STARTED'" +
//                " AND a.JOB_INSTANCE_ID = (SELECT JOB_INSTANCE_ID FROM BATCH_JOB_INSTANCE WHERE JOB_INSTANCE_ID = a.JOB_INSTANCE_ID)" +
//                " AND END_TIME IS NULL");
    }
}
