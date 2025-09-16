package com.ftn.sbnz.model.models.reports;

public class MaxPeriodReport {
    private String crossroad;
    private long durationMinutes;

    public MaxPeriodReport() {}

    public MaxPeriodReport(String crossroad, long durationMinutes) {
        this.crossroad = crossroad;
        this.durationMinutes = durationMinutes;
    }

    public String getCrossroad() { return crossroad; }
    public void setCrossroad(String crossroad) { this.crossroad = crossroad; }

    public long getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(long durationMinutes) { this.durationMinutes = durationMinutes; }

    @Override
    public String toString() {
        return "MaxPeriodReport{" +
                "crossroad='" + crossroad + '\'' +
                ", durationMinutes=" + durationMinutes +
                '}';
    }
}
