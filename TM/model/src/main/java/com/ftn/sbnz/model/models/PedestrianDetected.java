package com.ftn.sbnz.model.models;

import java.util.Date;

public class PedestrianDetected {
    private String crossroad;
    private int count; // number of pedestrians detected
    private Date timestamp;

    public PedestrianDetected() {
        this.timestamp = new Date();
    }
    
    public PedestrianDetected(String crossroad, int count) {
        this.crossroad = crossroad;
        this.count = count;
        this.timestamp = new Date();
    }
    
    public PedestrianDetected(String crossroad) {
        this.crossroad = crossroad;
        this.count = 1;
        this.timestamp = new Date();
    }

    public String getCrossroad() {
        return crossroad;
    }
    public void setCrossroad(String crossroad) {
        this.crossroad = crossroad;
    }

    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    
    public Date getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

}
