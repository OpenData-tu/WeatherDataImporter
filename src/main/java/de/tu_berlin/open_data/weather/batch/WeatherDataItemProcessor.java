package de.tu_berlin.open_data.weather.batch;

import de.tu_berlin.open_data.weather.model.WeatherData;
import de.tu_berlin.open_data.weather.service.ApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.InputMismatchException;

/**
 * Created by ahmadjawid on 5/21/17.
 */

public class WeatherDataItemProcessor implements ItemProcessor<WeatherData, WeatherData> {

    private static final Logger log = LoggerFactory.getLogger(WeatherDataItemProcessor.class);

    @Autowired
    ApplicationService applicationService;

    @Override
    public WeatherData process(final WeatherData weatherData) throws Exception {


        weatherData.setTimestamp(applicationService.toISODateFormat(weatherData.getTimestamp()));

        return weatherData;
    }

}
