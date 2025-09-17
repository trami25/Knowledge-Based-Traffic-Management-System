package com.ftn.sbnz.model.models;

public class PublicTransportDelay {
    private String line; // e.g. "Bus 42"
    private int delayMinutes; // e.g. 10

    public PublicTransportDelay() {
    }
    public PublicTransportDelay(String line, int delayMinutes) {
        this.line = line;
        this.delayMinutes = delayMinutes;
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
}
