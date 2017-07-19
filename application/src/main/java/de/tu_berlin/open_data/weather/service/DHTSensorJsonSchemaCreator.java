package de.tu_berlin.open_data.weather.service;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.tu_berlin.ise.open_data.model.Schema;
import de.tu_berlin.ise.open_data.service.JsonServiceImpl;
import de.tu_berlin.open_data.weather.model.DHTSensor;
import de.tu_berlin.open_data.weather.model.Extra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import de.tu_berlin.ise.open_data.service.ApplicationService;


/**
 * Created by ahmadjawid on 6/20/17.
 */
@Service
public class DHTSensorJsonSchemaCreator implements JsonSchemaCreator {

    @Autowired
    private JsonServiceImpl jsonService;

    @Override
    public String create(Schema dhtSensor) {

        DHTSensor dhtSensorItem = (DHTSensor) dhtSensor;

        jsonService.setSourceId(dhtSensorItem.getSourceId());
        jsonService.setDevice(dhtSensorItem.getSensorId());
        jsonService.setTimestamp(dhtSensorItem.getTimestamp());

        jsonService.setLocation(dhtSensorItem.getLat(), dhtSensorItem.getLon());

        jsonService.setLicense(dhtSensorItem.getLicense());

        jsonService.setSensor("temperature", dhtSensorItem.getSensorType(), dhtSensorItem.getTemperature());
        jsonService.setSensor("humidity", dhtSensorItem.getSensorType(), dhtSensorItem.getHumidity());

        Extra extra = new Extra(dhtSensorItem.getLocation());

        jsonService.setExtra(extra);


        return jsonService.build();

    }

}
