package de.tu_berlin.open_data.weather.http;

import org.springframework.core.io.UrlResource;

/**
 * Created by ahmadjawid on 6/20/17.
 */
public interface HttpService {


    UrlResource[] getUrlResources(String url, String sensorType);
}
