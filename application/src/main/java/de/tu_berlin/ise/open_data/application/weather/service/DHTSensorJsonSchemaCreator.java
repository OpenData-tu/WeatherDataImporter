package de.tu_berlin.ise.open_data.application.weather.service;

import de.tu_berlin.ise.open_data.library.model.Schema;
import de.tu_berlin.ise.open_data.library.service.JsonSchemaCreator;
import de.tu_berlin.ise.open_data.library.service.JsonStringBuilder;
import de.tu_berlin.ise.open_data.application.weather.model.DHTSensor;
import de.tu_berlin.ise.open_data.application.weather.model.Extra;
import org.json.JSONException;
import org.springframework.stereotype.Service;


/**
 * Created by ahmadjawid on 6/20/17.
 * Implementation of {@link JsonSchemaCreator}
 */
@Service
public class DHTSensorJsonSchemaCreator implements JsonSchemaCreator {




    /**
     * Get an objects which is extended from {@link Schema} class
     * and converts it to json
     * @param dhtSensor
     * @return String
     * */
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
