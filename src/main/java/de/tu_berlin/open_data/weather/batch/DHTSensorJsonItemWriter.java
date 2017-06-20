package de.tu_berlin.open_data.weather.batch;

import de.tu_berlin.open_data.weather.model.DHTSensor;
import de.tu_berlin.open_data.weather.model.Schema;
import de.tu_berlin.open_data.weather.model.WeatherData;
import de.tu_berlin.open_data.weather.service.JsonSchemaCreator;
import de.tu_berlin.open_data.weather.service.KafkaServiceRecordProducer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by ahmadjawid on 6/20/17.
 */
public class DHTSensorJsonItemWriter implements ItemWriter<Schema> {

    @Autowired
    JsonSchemaCreator jsonSchemaCreator;
    @Autowired
    KafkaServiceRecordProducer kafkaServiceRecordProducer;


    @Override
    public void write(List<? extends Schema> items) throws Exception {

        for (DHTSensor dhtSensorItem : (List<DHTSensor>)items){

            String jsonObject = jsonSchemaCreator.createForDHTSensor(dhtSensorItem);
            kafkaServiceRecordProducer.produce(jsonObject);
        }
    }
}
