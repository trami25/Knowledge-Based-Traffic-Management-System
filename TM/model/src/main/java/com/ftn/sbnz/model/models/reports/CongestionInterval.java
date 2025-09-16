package com.ftn.sbnz.model.models.reports;

public class CongestionInterval {
    private String crossroad;
    private long startTime;
    private long endTime;

    public CongestionInterval() {}

    public CongestionInterval(String crossroad, long startTime, long endTime) {
        this.crossroad = crossroad;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getCrossroad() { return crossroad; }
    public void setCrossroad(String crossroad) { this.crossroad = crossroad; }

    public long getStartTime() { return startTime; }
    public void setStartTime(long startTime) { this.startTime = startTime; }

    public long getEndTime() { return endTime; }
    public void setEndTime(long endTime) { this.endTime = endTime; }

    public long getDuration() {
        return (endTime - startTime) / 1000 / 60; // trajanje u minutima
    }
}
