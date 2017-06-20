package de.tu_berlin.open_data.weather.batch;

import de.tu_berlin.open_data.weather.model.DHTSensor;
import de.tu_berlin.open_data.weather.service.ApplicationService;
import kafka.utils.Json;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by ahmadjawid on 6/20/17.
 */
public class DHTSensorItemProcessor implements ItemProcessor<DHTSensor, DHTSensor> {

    @Autowired
    private ApplicationService applicationService;

    @Override
    public DHTSensor process(DHTSensor item) throws Exception {

        item.setTimestamp(applicationService.toISODateFormat(item.getTimestamp()));
        return item;
    }

    //TODO change output to json
//    @Override
//    public Json process(DHTSensor item) throws Exception {
//        return null;
//    }
}
