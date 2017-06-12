package de.tu_berlin.open_data.weather.batch;


import de.tu_berlin.open_data.weather.model.Schema;
import de.tu_berlin.open_data.weather.model.WeatherData;
import de.tu_berlin.open_data.weather.service.JsonSchemaCreator;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by ahmadjawid on 5/28/17.
 */
public class JsonItemWriter implements ItemWriter<Schema> {

    @Autowired
    JsonSchemaCreator jsonSchemaCreator;

    @Override
    public void write(List<? extends Schema> list) throws Exception {


        for (WeatherData currentWeatherData : (List<WeatherData>)list){

            System.out.println(jsonSchemaCreator.create(currentWeatherData));

        }


    }


}
