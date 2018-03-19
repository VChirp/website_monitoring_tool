package com.providesupport.monitoring.controler;

import com.providesupport.monitoring.dto.WebSiteResponse;
import com.providesupport.monitoring.entity.WebResource;
import com.providesupport.monitoring.service.CheckerService;
import com.providesupport.monitoring.service.WebResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/checks")
public class WebResourceController {
    private final CheckerService checkerService;
    private final WebResourceService webResourceService;

    @Autowired
    public WebResourceController(CheckerService checkerService, WebResourceService webResourceService) {
        this.checkerService = checkerService;
        this.webResourceService = webResourceService;
    }

    @PostMapping
    public WebResource addCheck(@RequestBody WebResource webResource) {
        return checkerService.check(webResource);
    }

    // TODO: cancel by ID or remove method to use addcheck instead
    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelCheck(@PathVariable Long id) {
        checkerService.cancel(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/response")
    public Map<String, WebSiteResponse> getResponse() {
        return checkerService.getResponseSet();
    }

    @GetMapping
    public List<WebResource> getAll() {
        return webResourceService.findAll();
    }
}
