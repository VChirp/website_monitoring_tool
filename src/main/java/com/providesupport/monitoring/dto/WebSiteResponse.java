package com.providesupport.monitoring.dto;

import com.providesupport.monitoring.entity.WebResource;
import lombok.Data;

@Data
public class WebSiteResponse {
    public enum State {
        OK, WARNING, CRITICAL
    }

    private WebResource webResource;
    private int responseCode;
    private State state;
    private String responseMessage;
    private long connectionTimeout;
    private int responseSize;

    public WebSiteResponse(WebResource webResource, int responseCode, String responseMessage, long connectionTimeout, int responseSize) {
        this.webResource = webResource;
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.connectionTimeout = connectionTimeout;
        this.responseSize = responseSize;
        if (this.getWebResource().getMinResponseSize() > responseSize || this.getWebResource().getMaxResponseSize() < responseSize) {
            this.state = transition(State.CRITICAL);
        } else if (responseCode != 200)
            this.state = transition(State.WARNING);
        else if (responseCode == 200)
            this.state = transition(State.OK);
    }

    private State transition(State state) {
        switch (state) {
            case OK:
                return State.OK;
            case WARNING:
                return State.WARNING;
            case CRITICAL:
                return State.CRITICAL;
            default:
                throw new RuntimeException("State exception");
        }
    }

    @Override
    public String toString() {
        return "WebSiteResponse{" +
                webResource.toString() + '\'' +
                ", responseCode=" + responseCode +
                ", state=" + state +
                ", responseMessage= " + responseMessage +
                ", responseSize= " + responseSize + "(B)" +
                ", connectionTimeout= " + connectionTimeout + "(ms)" +
                '}';
    }
}
