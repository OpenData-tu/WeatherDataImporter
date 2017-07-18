package de.tu_berlin.open_data.weather.service;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.tu_berlin.ise.open_data.model.Schema;
import de.tu_berlin.ise.open_data.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ahmadjawid on 7/13/17.
 */

@Service
public class JsonService {

//    public JsonService(Schema schema){
//
//    }

    @Autowired
    ApplicationService applicationService;

    JsonNodeFactory nodeFactory = JsonNodeFactory.instance;

    ObjectNode mainObject = nodeFactory.objectNode();

    ObjectNode firstLevelChild = nodeFactory.objectNode();

    ObjectNode secondLevelChild = nodeFactory.objectNode();

    ObjectNode sensors = nodeFactory.objectNode();

    private ObjectNode extra;

    void setSourceId(String sourceId) {

        mainObject.put("sourceId", sourceId);

    }

    void setDevice(String device) {
        mainObject.put("device", device);
    }

    void setTimestamp(String timestamp) {

        mainObject.put("timestamp", timestamp);
    }


    void setLocation(String lat, String lon) {
        firstLevelChild.put("lat", applicationService.parseToDouble(lat));
        firstLevelChild.put("lon", applicationService.parseToDouble(lon));
        mainObject.set("location", firstLevelChild);
       // firstLevelChild = nodeFactory.objectNode();
    }

    void setLicense(String license) {

        mainObject.put("license", license);

    }

    void setSensor(String measurement, String sensor, String observationValue) {
        secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", sensor);
        secondLevelChild.put("observation_value", applicationService.parseToDouble(observationValue));
        sensors.set(measurement, secondLevelChild);
    }

    public void setExtra(String fieldName, ObjectNode objectNode) {

        this.extra = objectNode;

    }

    //TODO is not finished - rethink from sensors part
    public String buildJson() {
        mainObject.set("sensors", sensors);
        mainObject.set("extra", extra);
        return mainObject.toString();
    }
}
