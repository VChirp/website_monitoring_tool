package com.providesupport.monitoring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.concurrent.TimeUnit;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class MonitoringPeriod {
    @NotNull
    @Min(1)
    private Integer count;
    @NotNull
    private TimeUnit timeUnit;

    @Override
    public String toString() {
        return count + " " + timeUnit;
    }
}
