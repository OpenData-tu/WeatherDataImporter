package de.tu_berlin.open_data.weather.service;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.tu_berlin.open_data.weather.model.WeatherData;
import org.springframework.stereotype.Service;

/**
 * Created by ahmadjawid on 6/9/17.
 */
@Service
public class JsonSchemaCreatorImpl implements JsonSchemaCreator {
    @Override
    public String create(WeatherData currentWeatherData) {
        JsonNodeFactory nodeFactory = JsonNodeFactory.instance;

        ObjectNode mainObject = nodeFactory.objectNode();

        mainObject.put("source_id", "luftdaten_info");
        mainObject.put("device", currentWeatherData.getSensorId());
        mainObject.put("timestamp", currentWeatherData.getTimestamp());
        //mainObject.put("timestamp_record", "");


        ObjectNode firstLevelChild = nodeFactory.objectNode();

        firstLevelChild.put("lat", currentWeatherData.getLat());
        firstLevelChild.put("lon", currentWeatherData.getLon());

        mainObject.set("location", firstLevelChild);

        mainObject.put("license", "find out");

        firstLevelChild = nodeFactory.objectNode();

        ObjectNode secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", currentWeatherData.getSensorType());
        secondLevelChild.put("observation_value", currentWeatherData.getPressure());
        firstLevelChild.set("pressure", secondLevelChild);

        secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", currentWeatherData.getSensorType());
        secondLevelChild.put("observation_value", currentWeatherData.getAltitude());
        firstLevelChild.set("altitude", secondLevelChild);

        secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", currentWeatherData.getSensorType());
        secondLevelChild.put("observation_value", currentWeatherData.getPressureSeaLevel());
        firstLevelChild.set("pressure_seallevel", secondLevelChild);

        secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", currentWeatherData.getSensorType());
        secondLevelChild.put("observation_value", currentWeatherData.getTemperature());
        firstLevelChild.set("temperature", secondLevelChild);

        secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", currentWeatherData.getSensorType());
        secondLevelChild.put("observation_value", currentWeatherData.getHumidity());
        firstLevelChild.set("humidity", secondLevelChild);



        mainObject.set("sensors", firstLevelChild);
        firstLevelChild = nodeFactory.objectNode();

        firstLevelChild.put("location", currentWeatherData.getLocation());
        mainObject.set("extra", firstLevelChild);


        return mainObject.toString();
    }
}
