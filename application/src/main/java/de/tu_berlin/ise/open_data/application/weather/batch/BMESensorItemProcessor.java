package de.tu_berlin.ise.open_data.application.weather.batch;

import de.tu_berlin.ise.open_data.library.service.ApplicationService;
import de.tu_berlin.ise.open_data.application.weather.model.BMESensor;
import de.tu_berlin.ise.open_data.application.weather.service.JsonSchemaCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by ahmadjawid on 5/21/17.
 */

public class BMESensorItemProcessor implements ItemProcessor<BMESensor, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BMESensorItemProcessor.class);

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    @Qualifier("BMESensorJsonSchemaCreator")
    private JsonSchemaCreator jsonSchemaCreator;

    @Override
    public String process(BMESensor item) throws Exception {
        item.setTimestamp(applicationService.toISODateFormat(item.getTimestamp()));
        return jsonSchemaCreator.create(item);
    }

}
