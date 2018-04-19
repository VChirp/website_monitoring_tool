package com.providesupport.monitoring.service;

import com.providesupport.monitoring.entity.MonitoringPeriod;
import com.providesupport.monitoring.entity.WebResource;
import com.providesupport.monitoring.dao.WebResourceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Transactional
@AllArgsConstructor
@Service
public class WebResourceService {
    private final WebResourceRepository repository;

    @PostConstruct
    public void init() {
        repository.save(new WebResource("http://www.google.com",
                new MonitoringPeriod(10, TimeUnit.MINUTES), 0, 1200000));

    }

    public void save(WebResource webResource) {
        repository.save(webResource);
    }

    public List<WebResource> findAll() {
        return repository.findAll();
    }

    public WebResource getById(Long id) {
        WebResource resource = repository.getOne(id);
        if (resource != null) {
            return resource;
        } else {
            throw new IllegalArgumentException("Resource not found:" + id);
        }
    }


}
