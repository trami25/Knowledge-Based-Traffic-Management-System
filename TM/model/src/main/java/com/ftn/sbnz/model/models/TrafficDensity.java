package com.ftn.sbnz.model.models;

import java.util.Date;

public class TrafficDensity {

    private String crossroad;
    private int value; // e.g. 45%
    private Date timestamp;

    public TrafficDensity() {
        this.timestamp = new Date();
    }
    
    public TrafficDensity(String crossroad, int value) {
        this.crossroad = crossroad;
        this.value = value;
        this.timestamp = new Date();
    }

    public String getCrossroad() {
        return crossroad;
    }
    public void setCrossroad(String crossroad) {
        this.crossroad = crossroad;
    }

    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
    
    public Date getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}