package com.ftn.sbnz.model.models;

import java.util.Date;

public class Weather {
    private String type; // e.g. sunny, rainy, snowy
    private String intensity; // e.g. light, moderate, heavy
    private Date timestamp;
    
    public Weather() {
        this.timestamp = new Date();
    }

    public Weather(String type, String intensity) {
        this.type = type;
        this.intensity = intensity;
        this.timestamp = new Date();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIntensity() {
        return intensity;
    }

    public void setIntensity(String intensity) {
        this.intensity = intensity;
    }
    
    public Date getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
