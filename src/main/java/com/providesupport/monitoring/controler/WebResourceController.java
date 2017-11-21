package com.providesupport.monitoring.controler;

import com.providesupport.monitoring.entity.WebResource;
import com.providesupport.monitoring.service.CheckerService;
import com.providesupport.monitoring.service.WebResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/checks")
public class WebResourceController {
    @Autowired
    private CheckerService checkerService;
    @Autowired
    private WebResourceService webResourceService;

    @PostMapping
    public WebResource addCheck(WebResource webResource) {
        return checkerService.check(webResource);
    }

    // TODO: cancel by ID or remove method to use addcheck instead
    @PostMapping("/cancel")
    public ResponseEntity<Void> cancelCheck(WebResource webResource) {
        checkerService.cancel(webResource);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<WebResource> getAll() {
        return webResourceService.findAll();
    }
}
