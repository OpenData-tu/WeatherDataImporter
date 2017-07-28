package de.tu_berlin.ise.open_data.service;

import java.util.concurrent.ExecutionException;

/**
 * Created by ahmadjawid on 7/12/17.
 */
public interface KafkaRecordProducer {

    void produce(String jsonObject) throws ExecutionException, InterruptedException;
}
