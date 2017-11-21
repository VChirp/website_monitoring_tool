package com.providesupport.monitoring.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Entity
@Table
public class WebResource {
    @Id
    @GeneratedValue
    private Long id;
    @URL
    @NotNull
    private String url;
    @Valid
    @NotNull
    @Embedded
    private MonitoringPeriod monitoringPeriod;
    private int minResponseSize;
    private int maxResponseSize;
    private String subStrResponse;
    private Boolean isCancelled = false;

    public WebResource(String url, MonitoringPeriod monitoringPeriod, int minResponseSize, int maxResponseSize) {
        this.url = url;
        this.monitoringPeriod = monitoringPeriod;
        this.minResponseSize = minResponseSize;
        this.maxResponseSize = maxResponseSize;
    }
}


