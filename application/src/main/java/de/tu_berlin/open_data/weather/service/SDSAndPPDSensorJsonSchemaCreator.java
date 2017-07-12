package de.tu_berlin.open_data.weather.service;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.tu_berlin.ise.open_data.model.Schema;
import de.tu_berlin.open_data.weather.model.SDSAndPPDSensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import de.tu_berlin.ise.open_data.service.ApplicationService;

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

        firstLevelChild.put("lat", applicationService.parseToDouble(sdsAndPPDSensorItem.getLat()));
        firstLevelChild.put("lon", applicationService.parseToDouble(sdsAndPPDSensorItem.getLon()));

        mainObject.set("location", firstLevelChild);

        mainObject.put("license", "find out");

        firstLevelChild = nodeFactory.objectNode();

        ObjectNode secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", sdsAndPPDSensorItem.getSensorType());
        secondLevelChild.put("observation_value", applicationService.parseToDouble(sdsAndPPDSensorItem.getDurP1()));
        firstLevelChild.set("p1", secondLevelChild);

        secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", sdsAndPPDSensorItem.getSensorType());
        secondLevelChild.put("observation_value", applicationService.parseToDouble(sdsAndPPDSensorItem.getDurP1()));
        firstLevelChild.set("durP1", secondLevelChild);

        secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", sdsAndPPDSensorItem.getSensorType());
        secondLevelChild.put("observation_value", applicationService.parseToDouble(sdsAndPPDSensorItem.getRatioP1()));
        firstLevelChild.set("ratioP1", secondLevelChild);

        secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", sdsAndPPDSensorItem.getSensorType());
        secondLevelChild.put("observation_value", applicationService.parseToDouble(sdsAndPPDSensorItem.getP2()));
        firstLevelChild.set("p2", secondLevelChild);

        secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", sdsAndPPDSensorItem.getSensorType());
        secondLevelChild.put("observation_value", applicationService.parseToDouble(sdsAndPPDSensorItem.getDurP2()));
        firstLevelChild.set("durP2", secondLevelChild);

        secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", sdsAndPPDSensorItem.getSensorType());
        secondLevelChild.put("observation_value", applicationService.parseToDouble(sdsAndPPDSensorItem.getRatioP2()));
        firstLevelChild.set("ratioP2", secondLevelChild);

        mainObject.set("sensors", firstLevelChild);
        firstLevelChild = nodeFactory.objectNode();

        firstLevelChild.put("location", applicationService.parseToDouble(sdsAndPPDSensorItem.getLocation()));
        mainObject.set("extra", firstLevelChild);


        return mainObject.toString();
    }
}
