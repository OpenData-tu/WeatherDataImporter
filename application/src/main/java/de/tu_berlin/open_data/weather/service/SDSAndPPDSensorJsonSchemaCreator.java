package de.tu_berlin.open_data.weather.service;

import de.tu_berlin.ise.open_data.model.Schema;
import de.tu_berlin.ise.open_data.service.JsonServiceImpl;
import de.tu_berlin.open_data.weather.model.Extra;
import de.tu_berlin.open_data.weather.model.SDSAndPPDSensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ahmadjawid on 6/20/17.
 */

@Service
public class SDSAndPPDSensorJsonSchemaCreator implements JsonSchemaCreator {

    @Autowired
    private JsonServiceImpl jsonService;

    @Override
    public String create(Schema schema) {
        SDSAndPPDSensor sdsAndPPDSensorItem = (SDSAndPPDSensor) schema;

        jsonService.setSourceId(sdsAndPPDSensorItem.getSourceId());
        jsonService.setDevice(sdsAndPPDSensorItem.getSensorId());
        jsonService.setTimestamp(sdsAndPPDSensorItem.getTimestamp());

        jsonService.setLocation(sdsAndPPDSensorItem.getLat(), sdsAndPPDSensorItem.getLon());

        jsonService.setLicense(sdsAndPPDSensorItem.getLicense());

        /**
         * For every measurement type call setSensor for that measurement types.
         * The result would be set of sensors
         */
        jsonService.setSensor("p1", sdsAndPPDSensorItem.getSensorType(), sdsAndPPDSensorItem.getP1());
        jsonService.setSensor("durP1", sdsAndPPDSensorItem.getSensorType(), sdsAndPPDSensorItem.getDurP1());
        jsonService.setSensor("ratioP1", sdsAndPPDSensorItem.getSensorType(), sdsAndPPDSensorItem.getRatioP1());
        jsonService.setSensor("p2", sdsAndPPDSensorItem.getSensorType(), sdsAndPPDSensorItem.getP2());
        jsonService.setSensor("durP2", sdsAndPPDSensorItem.getSensorType(), sdsAndPPDSensorItem.getDurP2());
        jsonService.setSensor("ratioP2", sdsAndPPDSensorItem.getSensorType(), sdsAndPPDSensorItem.getRatioP2());

        Extra extra = new Extra(sdsAndPPDSensorItem.getLocation());


        //Pass a Java object as extra attributes
        jsonService.setExtra(extra);

        return jsonService.build();
    }
}
