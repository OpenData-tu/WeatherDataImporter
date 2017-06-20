package de.tu_berlin.open_data.weather.batch;

import de.tu_berlin.open_data.weather.service.KafkaServiceRecordProducer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by ahmadjawid on 6/20/17.
 */
public class DHTSensorJsonItemWriter implements ItemWriter<String> {

    @Autowired
    KafkaServiceRecordProducer kafkaServiceRecordProducer;

    @Override
    public void write(List<? extends String> items) throws Exception {
        for (String jsonObject: items){
            kafkaServiceRecordProducer.produce(jsonObject);
        }
    }
}
