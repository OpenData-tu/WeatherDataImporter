package de.tu_berlin.ise.open_data.application.weather.service;

import de.tu_berlin.ise.open_data.application.weather.model.BMESensor;
import de.tu_berlin.ise.open_data.library.model.Schema;
import de.tu_berlin.ise.open_data.application.weather.model.Extra;
import org.json.JSONException;
import org.springframework.stereotype.Service;

import de.tu_berlin.ise.open_data.library.service.JsonStringBuilder;

/**
 * Created by ahmadjawid on 6/9/17.
 */
@Service
public class BMESensorJsonSchemaCreator implements JsonSchemaCreator {




    private BMESensor bmeSensorItem;
    @Override
    public String create(Schema schema) throws JSONException {
        bmeSensorItem = (BMESensor) schema;

        JsonStringBuilder jsonBuilder = new JsonStringBuilder();

        jsonBuilder.setSourceId(bmeSensorItem.getSourceId());
        jsonBuilder.setDevice(bmeSensorItem.getSensorId());
        jsonBuilder.setTimestamp(bmeSensorItem.getTimestamp());
        jsonBuilder.setLocation(bmeSensorItem.getLat(), bmeSensorItem.getLon());
        jsonBuilder.setLicense(bmeSensorItem.getLicense());

        jsonBuilder.setSensor("pressure", bmeSensorItem.getSensorType(), bmeSensorItem.getPressure());
        jsonBuilder.setSensor("altitude", bmeSensorItem.getSensorType(), bmeSensorItem.getAltitude());
        jsonBuilder.setSensor("pressure_sealevel", bmeSensorItem.getSensorType(), bmeSensorItem.getPressureSeaLevel());
        jsonBuilder.setSensor("temperature", bmeSensorItem.getSensorType(), bmeSensorItem.getTemperature());
        jsonBuilder.setSensor("humidity", bmeSensorItem.getSensorType(), bmeSensorItem.getHumidity());

        Extra extra = new Extra(bmeSensorItem.getLocation());

        jsonBuilder.setExtra(extra);

        return jsonBuilder.build();
    }
}
