package de.tu_berlin.open_data.weather.service;

import de.tu_berlin.ise.open_data.service.KafkaRecordProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by ahmadjawid on 6/13/17.
 */
@Service
public class KafkaServiceRecordProducerImpl implements KafkaServiceRecordProducer {

//    @Autowired
//    Producer producer;

    @Autowired
    KafkaRecordProducer kafkaRecordProducer;

    @Value("${kafka.topic}")
    private String topic;


    @Override
    public void produce(String jsonObject) {

      kafkaRecordProducer.produce(jsonObject);

     // producer.send(new ProducerRecord(topic, jsonObject));
        // producer.close();

    }
}
