package de.tu_berlin.ise.open_data.service;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by ahmadjawid on 7/12/17.
 */
public class KafkaRecordProducerImpl implements KafkaRecordProducer {


    @Autowired
    private Producer producer;


    @Value("${kafka.topic}")
    private String topic;


    @Override
    public void produce(String jsonObject) {


        System.out.println(jsonObject);

       // producer.send(new ProducerRecord(topic, jsonObject));
        // producer.close();

    }
}
