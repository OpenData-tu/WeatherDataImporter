package de.tu_berlin.ise.open_data.application.weather.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

/**
 * Created by ahmadjawid on 6/30/17.
 *Properties of resource are provided here.
 * These values are overridden by application.properties overridden by environment variables
 */

@Configuration
@ConfigurationProperties("resource")
public class ResourceProperties {

    private String url;

    public ResourceProperties(){

        setUrl(getPreviousDayUrl());
    }

    public String getUrl() {

        if (url == null) {

            setUrl(getPreviousDayUrl());

        }

        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String getPreviousDayUrl() {

        return "http://archive.luftdaten.info/" + LocalDate.now().minusDays(1).toString() + "/";
    }
}

