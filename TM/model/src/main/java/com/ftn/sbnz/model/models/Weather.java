package com.ftn.sbnz.model.models;

public class Weather {
    private String type; // e.g. sunny, rainy, snowy
    private String intensity; // e.g. light, moderate, heavy
    
    public Weather() {
    }

    public Weather(String type, String intensity) {
        this.type = type;
        this.intensity = intensity;
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
}
