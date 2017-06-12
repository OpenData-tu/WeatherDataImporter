package de.tu_berlin.open_data.weather.service;

import de.tu_berlin.open_data.weather.model.WeatherData;

/**
 * Created by ahmadjawid on 6/9/17.
 */
public interface JsonSchemaCreator {

     String create(WeatherData schemaList);
}
