package de.tu_berlin.ise.open_data.application.weather.batch;

import de.tu_berlin.ise.open_data.application.weather.http.HttpService;
import de.tu_berlin.ise.open_data.library.model.Schema;
import de.tu_berlin.ise.open_data.library.service.ApplicationService;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;

/**
 * Created by ahmadjawid on 7/19/17.
 */

public class CustomMultiResourceItemReader extends MultiResourceItemReader {


    void setProperties(HttpService httpFileDownloaderService, ApplicationService applicationService, String resourceUrl,
                       String sensorType, Class<? extends Schema> lineMapperClass) throws InstantiationException, IllegalAccessException {
        setResources(httpFileDownloaderService.getUrlResources(resourceUrl, sensorType));

        FlatFileItemReader reader = new FlatFileItemReader<>();

        reader.setLinesToSkip(1);

        reader.setLineMapper(applicationService.createLineMapper(lineMapperClass));

        setDelegate(reader);
    }
}
