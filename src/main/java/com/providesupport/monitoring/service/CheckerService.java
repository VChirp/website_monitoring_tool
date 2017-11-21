package com.providesupport.monitoring.service;

import com.providesupport.monitoring.dto.WebSiteResponse;
import com.providesupport.monitoring.entity.WebResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.*;

@Service
public class CheckerService {
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final WebResourceService webResourceService;
    private ConcurrentHashMap<Long, ScheduledFuture> checkerMap = new ConcurrentHashMap<>();

    @Autowired
    public CheckerService(WebResourceService webResourceService) {
        this.webResourceService = webResourceService;
        webResourceService.findAll().forEach(this::check);
    }

    public WebResource check(WebResource webResource) {
        webResourceService.save(webResource);

        removeFromMap(webResource);
        webResource.setIsCancelled(false);
        final ScheduledFuture<?> checkScheduler = scheduler.scheduleAtFixedRate(() -> {
            WebSiteResponse response = new Observer(webResource).pingWebsite();
            System.out.println(response.toString());
        }, 5, 5, TimeUnit.SECONDS);
        checkerMap.put(webResource.getId(), checkScheduler);

        return webResource;
    }

    public void cancel(WebResource webResource) {
        removeFromMap(webResource);
        webResource.setIsCancelled(true);
        webResourceService.save(webResource);
    }

    private void removeFromMap(WebResource webResource) {
        Long id = Objects.requireNonNull(webResource.getId());
        if (checkerMap.containsKey(id)) {
            checkerMap.remove(id).cancel(true);
        }
    }
    private static class Observer {


        private WebResource webResource;

        Observer(WebResource webResource) {
            this.webResource = webResource;
        }

        WebSiteResponse pingWebsite() {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(webResource.getUrl()).openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10000);
                long timeStart = System.currentTimeMillis();
                connection.connect();
                long timeEnd = System.currentTimeMillis();
                BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
                int responseCode = connection.getResponseCode();
                String responseMessage = connection.getResponseMessage();
                int responseSize = bis.available();
                return new WebSiteResponse(webResource, responseCode, responseMessage, timeEnd - timeStart, responseSize);
            } catch (IOException e) {
                throw new IllegalArgumentException("Cannot get response", e);
            }
        }

    }
}
