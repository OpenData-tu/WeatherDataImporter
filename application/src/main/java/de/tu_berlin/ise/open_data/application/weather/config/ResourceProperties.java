package de.tu_berlin.ise.open_data.application.weather.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

/**
 * Created by ahmadjawid on 6/30/17.
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


        }

        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPreviousDayUrl() {



        return "http://archive.luftdaten.info/" + LocalDate.now().minusDays(1).toString() + "/";
    }
}

