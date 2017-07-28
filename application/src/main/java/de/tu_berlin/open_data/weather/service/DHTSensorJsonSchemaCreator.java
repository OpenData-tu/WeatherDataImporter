package de.tu_berlin.open_data.weather.service;

import de.tu_berlin.ise.open_data.model.Schema;
import de.tu_berlin.ise.open_data.service.JsonStringBuilder;
import de.tu_berlin.open_data.weather.model.DHTSensor;
import de.tu_berlin.open_data.weather.model.Extra;
import org.json.JSONException;
import org.springframework.stereotype.Service;


/**
 * Created by ahmadjawid on 6/20/17.
 */
@Service
public class DHTSensorJsonSchemaCreator implements JsonSchemaCreator {



    @Override
    public String create(Schema dhtSensor) throws JSONException {

        DHTSensor dhtSensorItem = (DHTSensor) dhtSensor;

        JsonStringBuilder jsonBuilder = new JsonStringBuilder();

        jsonBuilder.setSourceId(dhtSensorItem.getSourceId());
        jsonBuilder.setDevice(dhtSensorItem.getSensorId());
        jsonBuilder.setTimestamp(dhtSensorItem.getTimestamp());

        jsonBuilder.setLocation(dhtSensorItem.getLat(), dhtSensorItem.getLon());

        jsonBuilder.setLicense(dhtSensorItem.getLicense());

        jsonBuilder.setSensor("temperature", dhtSensorItem.getSensorType(), dhtSensorItem.getTemperature());
        jsonBuilder.setSensor("humidity", dhtSensorItem.getSensorType(), dhtSensorItem.getHumidity());

        Extra extra = new Extra(dhtSensorItem.getLocation());

        jsonBuilder.setExtra(extra);


        return jsonBuilder.build();

    }

}
