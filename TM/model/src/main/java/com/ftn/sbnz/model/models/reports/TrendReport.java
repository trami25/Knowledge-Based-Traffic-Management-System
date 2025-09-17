package com.ftn.sbnz.model.models.reports;

import java.util.Date;

public class TrendReport {
    private String crossroad;
    private String period;
    private Date from;
    private Date to;
    private double avgDensity;
    private double avgSpeed;
    private int totalAccidents;

    public TrendReport() {}

    public TrendReport(String crossroad, String period, Date from, Date to, double avgDensity, double avgSpeed, int totalAccidents) {
        this.crossroad = crossroad;
        this.period = period;
        this.from = from;
        this.to = to;
        this.avgDensity = avgDensity;
        this.avgSpeed = avgSpeed;
        this.totalAccidents = totalAccidents;
    }

    public String getCrossroad() { return crossroad; }
    public void setCrossroad(String crossroad) { this.crossroad = crossroad; }

    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }

    public Date getFrom() { return from; }
    public void setFrom(Date from) { this.from = from; }

    public Date getTo() { return to; }
    public void setTo(Date to) { this.to = to; }

    public double getAvgDensity() { return avgDensity; }
    public void setAvgDensity(double avgDensity) { this.avgDensity = avgDensity; }

    public double getAvgSpeed() { return avgSpeed; }
    public void setAvgSpeed(double avgSpeed) { this.avgSpeed = avgSpeed; }

    public int getTotalAccidents() { return totalAccidents; }
    public void setTotalAccidents(int totalAccidents) { this.totalAccidents = totalAccidents; }
}
