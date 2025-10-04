package com.ftn.sbnz.model.models;

import java.util.Date;

public class Accident {
    private String crossroad;
    private String severity; // e.g. minor, major, fatal
    private Date timestamp;

    public Accident() {
        this.timestamp = new Date();
    }
    
    public Accident(String crossroad, String severity) {
        this.crossroad = crossroad;
        this.severity = severity;
        this.timestamp = new Date();
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
    
    public Date getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}