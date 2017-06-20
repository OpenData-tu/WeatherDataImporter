package de.tu_berlin.open_data.weather.service;

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
    public float parseToFloat(String number) {

        try {
            return Float.parseFloat(number);
        } catch (NumberFormatException e) {
            return 0;
        }

    }

    @Override
    public String toISODateFormat(String date) {
        return date + "Z";
    }
}
