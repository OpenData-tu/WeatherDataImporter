package de.tu_berlin.open_data.weather.service;

import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

/**
 * Created by ahmadjawid on 6/10/17.
 */

@Service
public class ApplicationServiceImpl implements ApplicationService {
    @Override
    public String[] getFields(Class<? extends Object> aClass) {

        Field[] aClassDeclaredFields = aClass.getDeclaredFields();

        String[] fieldsArray = new String[aClassDeclaredFields.length];

        for (int index = 0; index < aClassDeclaredFields.length; index++)
            fieldsArray[index] = aClassDeclaredFields[index].getName();

        return fieldsArray;
    }

    @Override
    public Double parseToDouble(String number) {

        try {
            return Double.parseDouble(number);
        } catch (NumberFormatException e) {
            return null;
        }

    }

    @Override
    public String toISODateFormat(String date) {
        if (date.contains("+")){
            return date;
        }
        return date + "Z";
    }

//    @Override
//    public LineMapper createLineMapper(Class<? extends Schema> aClass) throws IllegalAccessException, InstantiationException {
//        return new DefaultLineMapper<Schema>() {{
//            setLineTokenizer(new DelimitedLineTokenizer(aClass.newInstance().getDelimiter()) {{
//                setNames(getFields(aClass));
//            }});
//            setFieldSetMapper(new BeanWrapperFieldSetMapper<Schema>() {{
//                setTargetType(aClass);
//            }});
//        }};
//    }
}
