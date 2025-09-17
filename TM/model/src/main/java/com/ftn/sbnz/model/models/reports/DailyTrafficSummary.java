package com.ftn.sbnz.model.models.reports;

import java.util.Date;

public class DailyTrafficSummary {
    private String crossroad;
    private Date date;
    private String period; // npr. "07:00-09:00"
    public double avgDensity;
    public double avgSpeed;
    public int accidents;

    public DailyTrafficSummary() {
    }

    public DailyTrafficSummary(String crossroad, Date date, String period, double avgDensity, double avgSpeed,
            int accidents) {
        this.crossroad = crossroad;
        this.date = date;
        this.period = period;
        this.avgDensity = avgDensity;
        this.avgSpeed = avgSpeed;
        this.accidents = accidents;
    }

    public String getCrossroad() {
        return crossroad;
    }

    public void setCrossroad(String crossroad) {
        this.crossroad = crossroad;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public double getAvgDensity() {
        return avgDensity;
    }

    public void setAvgDensity(double avgDensity) {
        this.avgDensity = avgDensity;
    }

    public double getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(double avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public int getAccidents() {
        return accidents;
    }

    public void setAccidents(int accidents) {
        this.accidents = accidents;
    }
}
