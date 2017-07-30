package de.tu_berlin.ise.open_data.application.weather.http;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmadjawid on 6/20/17.
 * Implementation of {@link HttpService}
 */

@Service
public class HttpServiceImpl implements HttpService {


    /**
     * Service used to locate resources inside a specific date directory for luftdaten_info
     * @param url
     * @param sensorType
     * @return UrlResource[]
     * */
    @Override
    public UrlResource[] getUrlResources(String url, String sensorType) {

        if (!url.endsWith("/"))
            url = url + "/";


        List<UrlResource> urlResourceList = new ArrayList<>();

        try {
            //Connect to url, get a list document elements
            Document doc = Jsoup.connect(url).get();

            //Search for links (each link is a path to a single file)
            Elements links = doc.getElementsByTag("a");

            String href;
            for (Element link : links) {

                //In each job step we only need links to files which are of a specific sensor type
                //which is defined as the properties of reader
                if ((href = link.attr("href")).contains(sensorType) && href.endsWith(".csv")) {

                    urlResourceList.add(new UrlResource(new URL(url + href)));
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //Convert list urls to array and return
        return urlResourceList.toArray(new UrlResource[]{});

    }
}
