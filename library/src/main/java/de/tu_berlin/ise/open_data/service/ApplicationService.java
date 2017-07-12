package de.tu_berlin.ise.open_data.service;

import de.tu_berlin.ise.open_data.model.Schema;
import org.springframework.batch.item.file.LineMapper;

/**
 * Created by ahmadjawid on 7/12/17.
 */
public interface ApplicationService {

    String [] getFields(Class<? extends Object> aClass);
    Double parseToDouble(String number);
   // String toISODateFormat(String date);

    LineMapper createLineMapper(Class<? extends Schema> aClass) throws IllegalAccessException, InstantiationException;


}
