package de.tu_berlin.ise.open_data.application.weather.service;

import de.tu_berlin.ise.open_data.library.model.Schema;
import de.tu_berlin.ise.open_data.library.service.JsonStringBuilder;
import de.tu_berlin.ise.open_data.application.weather.model.Extra;
import de.tu_berlin.ise.open_data.application.weather.model.SDSAndPPDSensor;
import org.json.JSONException;
import org.springframework.stereotype.Service;

/**
 * Created by ahmadjawid on 6/20/17.
 */

@Service
public class SDSAndPPDSensorJsonSchemaCreator implements JsonSchemaCreator {


    @Override
    public String create(Schema schema) throws JSONException {
        SDSAndPPDSensor sdsAndPPDSensorItem = (SDSAndPPDSensor) schema;

        JsonStringBuilder jsonBuilder = new JsonStringBuilder();

        jsonBuilder.setSourceId(sdsAndPPDSensorItem.getSourceId());
        jsonBuilder.setDevice(sdsAndPPDSensorItem.getSensorId());
        jsonBuilder.setTimestamp(sdsAndPPDSensorItem.getTimestamp());

        jsonBuilder.setLocation(sdsAndPPDSensorItem.getLat(), sdsAndPPDSensorItem.getLon());

        jsonBuilder.setLicense(sdsAndPPDSensorItem.getLicense());

        /*
         * For every measurement type call setSensor for that measurement types.
         * The result would be set of sensors
         */
        jsonBuilder.setSensor("p1", sdsAndPPDSensorItem.getSensorType(), sdsAndPPDSensorItem.getP1());
        jsonBuilder.setSensor("durP1", sdsAndPPDSensorItem.getSensorType(), sdsAndPPDSensorItem.getDurP1());
        jsonBuilder.setSensor("ratioP1", sdsAndPPDSensorItem.getSensorType(), sdsAndPPDSensorItem.getRatioP1());
        jsonBuilder.setSensor("p2", sdsAndPPDSensorItem.getSensorType(), sdsAndPPDSensorItem.getP2());
        jsonBuilder.setSensor("durP2", sdsAndPPDSensorItem.getSensorType(), sdsAndPPDSensorItem.getDurP2());
        jsonBuilder.setSensor("ratioP2", sdsAndPPDSensorItem.getSensorType(), sdsAndPPDSensorItem.getRatioP2());

        Extra extra = new Extra(sdsAndPPDSensorItem.getLocation());


        //Pass a Java object as extra attributes
        jsonBuilder.setExtra(extra);

        return jsonBuilder.build();
    }
}
