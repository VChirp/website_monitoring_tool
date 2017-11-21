package com.providesupport.monitoring.controler;

import lombok.Data;
import org.hibernate.validator.constraints.URL;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/tests")
public class TestController {

    @PostMapping
    public Test createTest(@Valid Test test) {
       return test;
    }

    @Data
    static class Test {
        @NotNull
        @URL
        private String url;
    }
}
