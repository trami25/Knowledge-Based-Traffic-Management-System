package com.ftn.sbnz.model.models;

public class Accident {
    private String crossroad;
    private String severity; // e.g. minor, major, fatal


    public Accident() {
    }
    public Accident(String crossroad, String severity) {
        this.crossroad = crossroad;
        this.severity = severity;
    }

    public String getCrossroad() {
        return crossroad;
    }
    public void setCrossroad(String crossroad) {
        this.crossroad = crossroad;
    }

    public String getSeverity() {
        return severity;
    }
    public void setSeverity(String severity) {
        this.severity = severity;
    }
}