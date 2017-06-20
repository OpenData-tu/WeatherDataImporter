package de.tu_berlin.open_data.weather.http;

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
 */

@Service
public class HttpFileDownloaderServiceImpl implements HttpFileDownloaderService {
    @Override
    public UrlResource[] downloadFromUrl(String url, String sensorType) {

        url = "http://archive.luftdaten.info/2017-06-18/";

        // Document document = new Document("http://archive.luftdaten.info/2017-06-18/");
        List<UrlResource> urlResourceList = new ArrayList<>();


        try {
            Document doc = Jsoup.connect(url).get();
            Elements links = doc.getElementsByTag("a");
            UrlResource[] urlResources = new UrlResource[]{};



            String href;
            for (Element link : links) {
                if ((href = link.attr("href")).contains(sensorType) && href.endsWith(".csv")) {

                    // System.out.println(url + link.attr("href"));
                    urlResourceList.add(new UrlResource(new URL(url + href)));
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return urlResourceList.toArray(new UrlResource[]{});

        // System.out.println(document.getAllElements());
    }
}
