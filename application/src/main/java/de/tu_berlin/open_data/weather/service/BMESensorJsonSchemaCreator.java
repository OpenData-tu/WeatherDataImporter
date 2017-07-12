package de.tu_berlin.open_data.weather.service;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.tu_berlin.ise.open_data.model.Schema;
import de.tu_berlin.open_data.weather.model.BMESensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ahmadjawid on 6/9/17.
 */
@Service
public class BMESensorJsonSchemaCreator implements JsonSchemaCreator {

    @Autowired
    ApplicationService applicationService;
    @Override
    public String create(Schema schema) {
        BMESensor bmeSensorItem = (BMESensor) schema;

        JsonNodeFactory nodeFactory = JsonNodeFactory.instance;

        ObjectNode mainObject = nodeFactory.objectNode();

        mainObject.put("source_id", "luftdaten_info");
        mainObject.put("device", bmeSensorItem.getSensorId());
        mainObject.put("timestamp", bmeSensorItem.getTimestamp().toString());


        ObjectNode firstLevelChild = nodeFactory.objectNode();

        firstLevelChild.put("lat", applicationService.parseToDouble(bmeSensorItem.getLat()));
        firstLevelChild.put("lon", applicationService.parseToDouble(bmeSensorItem.getLon()));

        mainObject.set("location", firstLevelChild);

        mainObject.put("license", "find out");

        firstLevelChild = nodeFactory.objectNode();

        ObjectNode secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", bmeSensorItem.getSensorType());
        secondLevelChild.put("observation_value", applicationService.parseToDouble(bmeSensorItem.getPressure()));
        firstLevelChild.set("pressure", secondLevelChild);

        secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", bmeSensorItem.getSensorType());
        secondLevelChild.put("observation_value", applicationService.parseToDouble(bmeSensorItem.getAltitude()));
        firstLevelChild.set("altitude", secondLevelChild);

        secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", bmeSensorItem.getSensorType());
        secondLevelChild.put("observation_value", applicationService.parseToDouble(bmeSensorItem.getPressureSeaLevel()));
        firstLevelChild.set("pressure_sealevel", secondLevelChild);

        secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", bmeSensorItem.getSensorType());
        secondLevelChild.put("observation_value", applicationService.parseToDouble(bmeSensorItem.getTemperature()));
        firstLevelChild.set("temperature", secondLevelChild);

        secondLevelChild = nodeFactory.objectNode();
        secondLevelChild.put("sensor", bmeSensorItem.getSensorType());
        secondLevelChild.put("observation_value", applicationService.parseToDouble(bmeSensorItem.getHumidity()));
        firstLevelChild.set("humidity", secondLevelChild);



        mainObject.set("sensors", firstLevelChild);
        firstLevelChild = nodeFactory.objectNode();

        firstLevelChild.put("location", applicationService.parseToDouble(bmeSensorItem.getLocation()));
        mainObject.set("extra", firstLevelChild);


        return mainObject.toString();
    }
}
