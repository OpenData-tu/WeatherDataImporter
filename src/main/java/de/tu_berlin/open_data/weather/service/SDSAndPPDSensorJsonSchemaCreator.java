package de.tu_berlin.open_data.weather.service;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.tu_berlin.open_data.weather.model.SDSAndPPDSensor;
import de.tu_berlin.open_data.weather.model.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ahmadjawid on 6/20/17.
 */

@Service
public class SDSAndPPDSensorJsonSchemaCreator implements JsonSchemaCreator {

    @Autowired
    private ApplicationService applicationService;
    @Override
    public String create(Schema schema) {
        SDSAndPPDSensor sdsAndPPDSensorItem = (SDSAndPPDSensor) schema;
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
