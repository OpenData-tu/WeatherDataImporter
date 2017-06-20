package de.tu_berlin.open_data.weather.service;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.tu_berlin.open_data.weather.model.DHTSensor;
import de.tu_berlin.open_data.weather.model.SDSAndPPDSensor;
import de.tu_berlin.open_data.weather.model.WeatherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ahmadjawid on 6/9/17.
 */
@Service
public class JsonSchemaCreatorImpl implements JsonSchemaCreator {

    @Autowired
    ApplicationService applicationService;
    @Override
    public String create(WeatherData currentWeatherData) {
        JsonNodeFactory nodeFactory = JsonNodeFactory.instance;

        ObjectNode mainObject = nodeFactory.objectNode();

        mainObject.put("source_id", "luftdaten_info");
        mainObject.put("device", currentWeatherData.getSensorId());
        mainObject.put("timestamp", currentWeatherData.getTimestamp().toString());
        //mainObject.put("timestamp_record", "");


        ObjectNode firstLevelChild = nodeFactory.objectNode();

        firstLevelChild.put("lat", applicationService.parseToFloat(currentWeatherData.getLat()));
        firstLevelChild.put("lon", applicationService.parseToFloat(currentWeatherData.getLon()));

        mainObject.set("location", firstLevelChild);

        mainObject.put("license", "find out");

        firstLevelChild = nodeFactory.objectNode();

        ObjectNode secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", currentWeatherData.getSensorType());
        secondLevelChild.put("observation_value", applicationService.parseToFloat(currentWeatherData.getPressure()));
        firstLevelChild.set("pressure", secondLevelChild);

        secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", currentWeatherData.getSensorType());
        secondLevelChild.put("observation_value", applicationService.parseToFloat(currentWeatherData.getAltitude()));
        firstLevelChild.set("altitude", secondLevelChild);

        secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", currentWeatherData.getSensorType());
        secondLevelChild.put("observation_value", applicationService.parseToFloat(currentWeatherData.getPressureSeaLevel()));
        firstLevelChild.set("pressure_seallevel", secondLevelChild);

        secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", currentWeatherData.getSensorType());
        secondLevelChild.put("observation_value", applicationService.parseToFloat(currentWeatherData.getTemperature()));
        firstLevelChild.set("temperature", secondLevelChild);

        secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", currentWeatherData.getSensorType());
        secondLevelChild.put("observation_value", applicationService.parseToFloat(currentWeatherData.getHumidity()));
        firstLevelChild.set("humidity", secondLevelChild);



        mainObject.set("sensors", firstLevelChild);
        firstLevelChild = nodeFactory.objectNode();

        firstLevelChild.put("location", applicationService.parseToFloat(currentWeatherData.getLocation()));
        mainObject.set("extra", firstLevelChild);


        return mainObject.toString();
    }

    @Override
    public String createForDHTSensor(DHTSensor dhtSensorItem) {

        JsonNodeFactory nodeFactory = JsonNodeFactory.instance;

        ObjectNode mainObject = nodeFactory.objectNode();

        mainObject.put("source_id", "luftdaten_info");
        mainObject.put("device", dhtSensorItem.getSensorId());
        mainObject.put("timestamp", dhtSensorItem.getTimestamp().toString());
        //mainObject.put("timestamp_record", "");


        ObjectNode firstLevelChild = nodeFactory.objectNode();

        firstLevelChild.put("lat", applicationService.parseToFloat(dhtSensorItem.getLat()));
        firstLevelChild.put("lon", applicationService.parseToFloat(dhtSensorItem.getLon()));

        mainObject.set("location", firstLevelChild);

        mainObject.put("license", "find out");

        firstLevelChild = nodeFactory.objectNode();

        ObjectNode secondLevelChild = nodeFactory.objectNode();

        secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", dhtSensorItem.getSensorType());
        secondLevelChild.put("observation_value", applicationService.parseToFloat(dhtSensorItem.getTemperature()));
        firstLevelChild.set("temperature", secondLevelChild);

        secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", dhtSensorItem.getSensorType());
        secondLevelChild.put("observation_value", applicationService.parseToFloat(dhtSensorItem.getHumidity()));
        firstLevelChild.set("humidity", secondLevelChild);



        mainObject.set("sensors", firstLevelChild);
        firstLevelChild = nodeFactory.objectNode();

        firstLevelChild.put("location", applicationService.parseToFloat(dhtSensorItem.getLocation()));
        mainObject.set("extra", firstLevelChild);


        return mainObject.toString();
    }

    @Override
    public String createForSDSSensor(SDSAndPPDSensor sdsAndPPDSensorItem) {
        JsonNodeFactory nodeFactory = JsonNodeFactory.instance;

        ObjectNode mainObject = nodeFactory.objectNode();

        mainObject.put("source_id", "luftdaten_info");
        mainObject.put("device", sdsAndPPDSensorItem.getSensorId());
        mainObject.put("timestamp", sdsAndPPDSensorItem.getTimestamp().toString());
        //mainObject.put("timestamp_record", "");


        ObjectNode firstLevelChild = nodeFactory.objectNode();

        firstLevelChild.put("lat", applicationService.parseToFloat(sdsAndPPDSensorItem.getLat()));
        firstLevelChild.put("lon", applicationService.parseToFloat(sdsAndPPDSensorItem.getLon()));

        mainObject.set("location", firstLevelChild);

        mainObject.put("license", "find out");

        firstLevelChild = nodeFactory.objectNode();

        ObjectNode secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", sdsAndPPDSensorItem.getSensorType());
        secondLevelChild.put("observation_value", applicationService.parseToFloat(sdsAndPPDSensorItem.getDurP1()));
        firstLevelChild.set("p1", secondLevelChild);

        secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", sdsAndPPDSensorItem.getSensorType());
        secondLevelChild.put("observation_value", applicationService.parseToFloat(sdsAndPPDSensorItem.getDurP1()));
        firstLevelChild.set("durP1", secondLevelChild);

        secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", sdsAndPPDSensorItem.getSensorType());
        secondLevelChild.put("observation_value", applicationService.parseToFloat(sdsAndPPDSensorItem.getRatioP1()));
        firstLevelChild.set("ratioP1", secondLevelChild);

        secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", sdsAndPPDSensorItem.getSensorType());
        secondLevelChild.put("observation_value", applicationService.parseToFloat(sdsAndPPDSensorItem.getP2()));
        firstLevelChild.set("p2", secondLevelChild);

        secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", sdsAndPPDSensorItem.getSensorType());
        secondLevelChild.put("observation_value", applicationService.parseToFloat(sdsAndPPDSensorItem.getDurP2()));
        firstLevelChild.set("durP2", secondLevelChild);

        secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", sdsAndPPDSensorItem.getSensorType());
        secondLevelChild.put("observation_value", applicationService.parseToFloat(sdsAndPPDSensorItem.getRatioP2()));
        firstLevelChild.set("ratioP2", secondLevelChild);

        mainObject.set("sensors", firstLevelChild);
        firstLevelChild = nodeFactory.objectNode();

        firstLevelChild.put("location", applicationService.parseToFloat(sdsAndPPDSensorItem.getLocation()));
        mainObject.set("extra", firstLevelChild);


        return mainObject.toString();
    }
}
