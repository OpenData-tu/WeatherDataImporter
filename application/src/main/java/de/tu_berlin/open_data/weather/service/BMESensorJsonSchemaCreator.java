package de.tu_berlin.open_data.weather.service;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.tu_berlin.ise.open_data.model.Schema;
import de.tu_berlin.open_data.weather.model.BMESensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import de.tu_berlin.ise.open_data.service.ApplicationService;

/**
 * Created by ahmadjawid on 6/9/17.
 */
@Service
public class BMESensorJsonSchemaCreator implements JsonSchemaCreator {

    @Autowired
    ApplicationService applicationService;

    @Autowired
    private JsonService jsonService;
    @Override
    public String create(Schema schema) {
        BMESensor bmeSensorItem = (BMESensor) schema;

        JsonNodeFactory nodeFactory = JsonNodeFactory.instance;

        jsonService.setSourceId("luftdaten_info");
        jsonService.setDevice(bmeSensorItem.getSensorId());
        jsonService.setTimestamp(bmeSensorItem.getTimestamp().toString());
        jsonService.setLocation(bmeSensorItem.getLat(), bmeSensorItem.getLon());
        jsonService.setLicense("Find out");

        jsonService.setSensor("pressure", bmeSensorItem.getSensorType(), bmeSensorItem.getPressure());
        jsonService.setSensor("altitude", bmeSensorItem.getSensorType(), bmeSensorItem.getAltitude());
        jsonService.setSensor("pressure_sealevel", bmeSensorItem.getSensorType(), bmeSensorItem.getPressureSeaLevel());
        jsonService.setSensor("temperature", bmeSensorItem.getSensorType(), bmeSensorItem.getTemperature());
        jsonService.setSensor("humidity", bmeSensorItem.getSensorType(), bmeSensorItem.getHumidity());

        ObjectNode firstLevelChildHere = nodeFactory.objectNode();
        firstLevelChildHere.put("location", applicationService.parseToDouble(bmeSensorItem.getLocation()));

        jsonService.setExtra("location", firstLevelChildHere);


        return jsonService.buildJson();
    }
}
