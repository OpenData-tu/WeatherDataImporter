package de.tu_berlin.ise.open_data.application.weather.http;

import org.springframework.core.io.UrlResource;

/**
 * Created by ahmadjawid on 6/20/17.
 * Helper class to for doing some http operations
 */
public interface HttpService {


    /**
     * Service used to locate resources inside a specific date directory for luftdaten_info
     * @param url
     * @param sensorType
     * @return UrlResource[]
     * */
    UrlResource[] getUrlResources(String url, String sensorType);
}
