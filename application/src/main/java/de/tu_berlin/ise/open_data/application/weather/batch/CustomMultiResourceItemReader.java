package de.tu_berlin.ise.open_data.application.weather.batch;

import de.tu_berlin.ise.open_data.application.weather.http.HttpService;
import de.tu_berlin.ise.open_data.library.model.Schema;
import de.tu_berlin.ise.open_data.library.service.ApplicationService;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;

/**
 * Created by ahmadjawid on 7/19/17.
 * Reader which specifies how to read data from the source
 */

public class CustomMultiResourceItemReader extends MultiResourceItemReader {


    void setProperties(HttpService httpFileDownloaderService, ApplicationService applicationService, String resourceUrl,
                       String sensorType, Class<? extends Schema> lineMapperClass) throws InstantiationException, IllegalAccessException {
        //Each day includes a folder with a long list of files. Sets the resource to the list of files
        setResources(httpFileDownloaderService.getUrlResources(resourceUrl, sensorType));

        FlatFileItemReader reader = new FlatFileItemReader<>();

        // Set n first lines to skip parsing
        reader.setLinesToSkip(1);

        //Set LineMapper which defines how source rows are parsed into Java Objects
        reader.setLineMapper(applicationService.createLineMapper(lineMapperClass));

        setDelegate(reader);
    }
}
