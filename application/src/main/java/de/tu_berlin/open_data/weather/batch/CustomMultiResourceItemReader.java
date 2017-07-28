package de.tu_berlin.open_data.weather.batch;

import de.tu_berlin.ise.open_data.model.Schema;
import de.tu_berlin.ise.open_data.service.ApplicationService;
import de.tu_berlin.open_data.weather.http.HttpService;
import de.tu_berlin.open_data.weather.model.BMESensor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by ahmadjawid on 7/19/17.
 */

public class CustomMultiResourceItemReader extends MultiResourceItemReader {


    private ApplicationService applicationService;

    private HttpService httpService;

    private String resourceUrl;

    @Autowired
    public void setHttpService(HttpService httpService) {
        this.httpService = httpService;
    }

//    @Value("resource.url")
//    private String resourceUrl;

    void setProperties(HttpService httpFileDownloaderService, ApplicationService applicationService, String resourceUrl,
                       String sensorType, Class<? extends Schema> lineMapperClass) throws InstantiationException, IllegalAccessException {
        setResources(httpFileDownloaderService.getUrlResources(resourceUrl, sensorType));

        FlatFileItemReader reader = new FlatFileItemReader<>();

        reader.setLinesToSkip(1);


        reader.setLineMapper(applicationService.createLineMapper(lineMapperClass));

        setDelegate(reader);
    }


    public ApplicationService getApplicationService() {
        return applicationService;
    }

    public void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    public HttpService getHttpService() {
        return httpService;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }
}
