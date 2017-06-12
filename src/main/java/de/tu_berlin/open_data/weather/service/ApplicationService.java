package de.tu_berlin.open_data.weather.service;

/**
 * Created by ahmadjawid on 6/10/17.
 */
public interface ApplicationService {
    String [] getFields(Class<? extends Object> weatherDataClass);
}
