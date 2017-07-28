package de.tu_berlin.ise.open_data.batch;


import de.tu_berlin.ise.open_data.service.KafkaRecordProducer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by ahmadjawid on 5/28/17.
 */
public class JsonItemWriter implements ItemWriter<String> {

    @Autowired
    private KafkaRecordProducer kafkaRecordProducer;


    @Override
    public void write(List<? extends String> items) throws Exception {

       // System.out.println(items.get(0));

        for (String jsonObject : items) {
            kafkaRecordProducer.produce(jsonObject);
        }

    }
}
