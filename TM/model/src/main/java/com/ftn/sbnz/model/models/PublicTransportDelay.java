package com.ftn.sbnz.model.models;

import java.util.Date;

public class PublicTransportDelay {
    private String line; // e.g. "Bus 42"
    private int delayMinutes; // e.g. 10
    private Date timestamp;

    public PublicTransportDelay() {
        this.timestamp = new Date();
    }
    
    public PublicTransportDelay(String line, int delayMinutes) {
        this.line = line;
        this.delayMinutes = delayMinutes;
        this.timestamp = new Date();
    }

    public String getLine() {
        return line;
    }
    public void setLine(String line) {
        this.line = line;
    }

    public int getDelayMinutes() {
        return delayMinutes;
    }
    public void setDelayMinutes(int delayMinutes) {
        this.delayMinutes = delayMinutes;
    }
    
    public Date getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

}
