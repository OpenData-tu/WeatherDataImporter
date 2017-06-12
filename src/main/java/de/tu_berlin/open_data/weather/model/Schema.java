package de.tu_berlin.open_data.weather.model;

/**
 * Created by ahmadjawid on 5/21/17.
 */
public abstract class Schema {

    public abstract String [] getClassVariableNames();

    public abstract String getDelimiter();


    public abstract void process();

    public abstract void preProcess();
}
