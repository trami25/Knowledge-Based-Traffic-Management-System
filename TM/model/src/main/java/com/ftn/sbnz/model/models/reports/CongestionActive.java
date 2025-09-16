package com.ftn.sbnz.model.models.reports;

public class CongestionActive {
    private String crossroad;
    private long startTime;

    public CongestionActive() {}

    public CongestionActive(String crossroad, long startTime) {
        this.crossroad = crossroad;
        this.startTime = startTime;
    }

    public String getCrossroad() { return crossroad; }
    public void setCrossroad(String crossroad) { this.crossroad = crossroad; }

    public long getStartTime() { return startTime; }
    public void setStartTime(long startTime) { this.startTime = startTime; }
}
