package de.tu_berlin.open_data.weather.service;

import de.tu_berlin.ise.open_data.model.Schema;
import de.tu_berlin.open_data.weather.model.BMESensor;
import de.tu_berlin.open_data.weather.model.Extra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.tu_berlin.ise.open_data.service.JsonServiceImpl;

/**
 * Created by ahmadjawid on 6/9/17.
 */
@Service
public class BMESensorJsonSchemaCreator implements JsonSchemaCreator {

    @Autowired
    private JsonServiceImpl jsonServiceImpl;
    @Override
    public String create(Schema schema) {
        BMESensor bmeSensorItem = (BMESensor) schema;

        jsonServiceImpl.setSourceId(bmeSensorItem.getSourceId());
        jsonServiceImpl.setDevice(bmeSensorItem.getSensorId());
        jsonServiceImpl.setTimestamp(bmeSensorItem.getTimestamp());
        jsonServiceImpl.setLocation(bmeSensorItem.getLat(), bmeSensorItem.getLon());
        jsonServiceImpl.setLicense(bmeSensorItem.getLicense());

        jsonServiceImpl.setSensor("pressure", bmeSensorItem.getSensorType(), bmeSensorItem.getPressure());
        jsonServiceImpl.setSensor("altitude", bmeSensorItem.getSensorType(), bmeSensorItem.getAltitude());
        jsonServiceImpl.setSensor("pressure_sealevel", bmeSensorItem.getSensorType(), bmeSensorItem.getPressureSeaLevel());
        jsonServiceImpl.setSensor("temperature", bmeSensorItem.getSensorType(), bmeSensorItem.getTemperature());
        jsonServiceImpl.setSensor("humidity", bmeSensorItem.getSensorType(), bmeSensorItem.getHumidity());

        Extra extra = new Extra(bmeSensorItem.getLocation());

        jsonServiceImpl.setExtra(extra);

        return jsonServiceImpl.build();
    }
}
