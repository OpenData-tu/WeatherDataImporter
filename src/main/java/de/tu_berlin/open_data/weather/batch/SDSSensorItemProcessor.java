package de.tu_berlin.open_data.weather.batch;

import de.tu_berlin.open_data.weather.model.SDSSensor;
import de.tu_berlin.open_data.weather.service.ApplicationService;
import de.tu_berlin.open_data.weather.service.JsonSchemaCreator;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by ahmadjawid on 6/20/17.
 */
public class SDSSensorItemProcessor implements ItemProcessor<SDSSensor, String> {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private JsonSchemaCreator jsonSchemaCreator;
    @Override
    public String process(SDSSensor sdsSensorItem) throws Exception {
        sdsSensorItem.setTimestamp(applicationService.toISODateFormat(sdsSensorItem.getTimestamp()));

        return jsonSchemaCreator.createForSDSSensor(sdsSensorItem);
    }
}
