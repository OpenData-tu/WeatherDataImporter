package de.tu_berlin.ise.open_data.application.weather.batch;

import de.tu_berlin.ise.open_data.library.service.ApplicationService;
import de.tu_berlin.ise.open_data.application.weather.model.BMESensor;
import de.tu_berlin.ise.open_data.library.service.JsonSchemaCreator;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by ahmadjawid on 5/21/17.
 * Processing includes converting Java Objects to json string objects according our defined schema
 */

public class BMESensorItemProcessor implements ItemProcessor<BMESensor, String> {


    @Autowired
    private ApplicationService applicationService;

    @Autowired
    @Qualifier("BMESensorJsonSchemaCreator")
    private JsonSchemaCreator jsonSchemaCreator;

    @Override
    public String process(BMESensor item) throws Exception {
        item.setTimestamp(applicationService.toISODateTimeFormat(item.getTimestamp()));
        //A valid json objects is created and returned
        return jsonSchemaCreator.create(item);
    }

}
