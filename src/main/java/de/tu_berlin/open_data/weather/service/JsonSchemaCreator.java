package de.tu_berlin.open_data.weather.service;

import de.tu_berlin.open_data.weather.model.DHTSensor;
import de.tu_berlin.open_data.weather.model.SDSAndPPDSensor;
import de.tu_berlin.open_data.weather.model.WeatherData;

/**
 * Created by ahmadjawid on 6/9/17.
 */
public interface JsonSchemaCreator {

    //TODO make only one create method and distribute other methods into classes that implements JsonSchemaCreator

     String create(WeatherData schemaList);

    String createForDHTSensor(DHTSensor dhtSensorItem);

    String createForSDSSensor(SDSAndPPDSensor sdsAndPPDSensorItem);
}
