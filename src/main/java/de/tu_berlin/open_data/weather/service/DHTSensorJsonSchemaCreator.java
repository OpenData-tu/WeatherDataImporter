package de.tu_berlin.open_data.weather.service;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.tu_berlin.open_data.weather.model.DHTSensor;
import de.tu_berlin.open_data.weather.model.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ahmadjawid on 6/20/17.
 */
@Service
public class DHTSensorJsonSchemaCreator implements JsonSchemaCreator {

    @Autowired
    private ApplicationService applicationService;

    @Override
    public String create(Schema dhtSensor) {

        DHTSensor dhtSensorItem = (DHTSensor) dhtSensor;

        JsonNodeFactory nodeFactory = JsonNodeFactory.instance;

        ObjectNode mainObject = nodeFactory.objectNode();

        mainObject.put("source_id", "luftdaten_info");
        mainObject.put("device", dhtSensorItem.getSensorId());
        mainObject.put("timestamp", dhtSensorItem.getTimestamp().toString());
        //mainObject.put("timestamp_record", "");


        ObjectNode firstLevelChild = nodeFactory.objectNode();

        firstLevelChild.put("lat", applicationService.parseToDouble(dhtSensorItem.getLat()));
        firstLevelChild.put("lon", applicationService.parseToDouble(dhtSensorItem.getLon()));

        mainObject.set("location", firstLevelChild);

        mainObject.put("license", "find out");

        firstLevelChild = nodeFactory.objectNode();

        ObjectNode secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", dhtSensorItem.getSensorType());
        secondLevelChild.put("observation_value", applicationService.parseToDouble(dhtSensorItem.getTemperature()));
        firstLevelChild.set("temperature", secondLevelChild);

        secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", dhtSensorItem.getSensorType());
        secondLevelChild.put("observation_value", applicationService.parseToDouble(dhtSensorItem.getHumidity()));
        firstLevelChild.set("humidity", secondLevelChild);



        mainObject.set("sensors", firstLevelChild);
        firstLevelChild = nodeFactory.objectNode();

        firstLevelChild.put("location", applicationService.parseToDouble(dhtSensorItem.getLocation()));
        mainObject.set("extra", firstLevelChild);


        return mainObject.toString();

    }

}
