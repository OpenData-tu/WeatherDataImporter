package de.tu_berlin.ise.open_data.application.weather.service;

import de.tu_berlin.ise.open_data.library.model.Schema;
import org.json.JSONException;

/**
 * Created by ahmadjawid on 6/9/17.
 */
public interface JsonSchemaCreator {

     String create(Schema schema) throws JSONException;
}
