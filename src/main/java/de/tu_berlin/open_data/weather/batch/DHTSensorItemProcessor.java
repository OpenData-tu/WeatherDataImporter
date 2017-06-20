package de.tu_berlin.open_data.weather.batch;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.tu_berlin.open_data.weather.model.DHTSensor;
import de.tu_berlin.open_data.weather.service.ApplicationService;
import de.tu_berlin.open_data.weather.service.JsonSchemaCreator;
import kafka.utils.Json;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by ahmadjawid on 6/20/17.
 */
public class DHTSensorItemProcessor implements ItemProcessor<DHTSensor, String> {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private JsonSchemaCreator jsonSchemaCreator;

    @Override
    public String process(DHTSensor dhtSensorItem) throws Exception {

        dhtSensorItem.setTimestamp(applicationService.toISODateFormat(dhtSensorItem.getTimestamp()));

        return jsonSchemaCreator.createForDHTSensor(dhtSensorItem);

    }
}
