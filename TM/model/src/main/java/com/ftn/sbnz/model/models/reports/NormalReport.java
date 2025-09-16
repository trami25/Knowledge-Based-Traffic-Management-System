package com.ftn.sbnz.model.models.reports;

public class NormalReport {
    private long generatedAt;
    private String crossroad;
    private double avgSpeed5min;
    private int trafficDensity;
    private int accidents15min;
    private int pedestrians10min;

    public NormalReport() {}

    public NormalReport(String crossroad, double avgSpeed5min, int trafficDensity, int accidents15min, int pedestrians10min) {
        this.generatedAt = System.currentTimeMillis();
        this.crossroad = crossroad;
        this.avgSpeed5min = avgSpeed5min;
        this.trafficDensity = trafficDensity;
        this.accidents15min = accidents15min;
        this.pedestrians10min = pedestrians10min;
    }

    public long getGeneratedAt() { return generatedAt; }

    public String getCrossroad() { return crossroad; }
    public void setCrossroad(String crossroad) { this.crossroad = crossroad; }

    public double getAvgSpeed5min() { return avgSpeed5min; }
    public void setAvgSpeed5min(double avgSpeed5min) { this.avgSpeed5min = avgSpeed5min; }

    public int getTrafficDensity() { return trafficDensity; }
    public void setTrafficDensity(int trafficDensity) { this.trafficDensity = trafficDensity; }

    public int getAccidents15min() { return accidents15min; }
    public void setAccidents15min(int accidents15min) { this.accidents15min = accidents15min; }

    public int getPedestrians10min() { return pedestrians10min; }
    public void setPedestrians10min(int pedestrians10min) { this.pedestrians10min = pedestrians10min; }

    @Override
    public String toString() {
        return "NormalReport{" +
                "generatedAt=" + generatedAt +
                ", crossroad='" + crossroad + '\'' +
                ", avgSpeed5min=" + avgSpeed5min +
                ", trafficDensity=" + trafficDensity +
                ", accidents15min=" + accidents15min +
                ", pedestrians10min=" + pedestrians10min +
                '}';
    }
}
