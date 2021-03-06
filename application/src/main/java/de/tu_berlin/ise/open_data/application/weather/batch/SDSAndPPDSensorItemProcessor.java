package de.tu_berlin.ise.open_data.application.weather.batch;

import de.tu_berlin.ise.open_data.library.service.ApplicationService;
import de.tu_berlin.ise.open_data.application.weather.model.SDSAndPPDSensor;
import de.tu_berlin.ise.open_data.library.service.JsonSchemaCreator;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by ahmadjawid on 6/20/17.
 * Processing includes converting Java Objects to json string objects according our defined schema
 */
public class SDSAndPPDSensorItemProcessor implements ItemProcessor<SDSAndPPDSensor, String> {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    @Qualifier("SDSAndPPDSensorJsonSchemaCreator")
    private JsonSchemaCreator jsonSchemaCreator;
    @Override
    public String process(SDSAndPPDSensor sdsAndPPDSensorItem) throws Exception {
        sdsAndPPDSensorItem.setTimestamp(applicationService.toISODateTimeFormat(sdsAndPPDSensorItem.getTimestamp()));
        //A valid json objects is created and returned
        return jsonSchemaCreator.create(sdsAndPPDSensorItem);
    }
}
