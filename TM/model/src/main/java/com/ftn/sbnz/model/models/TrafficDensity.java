package com.ftn.sbnz.model.models;

public class TrafficDensity {

    private String crossroad;
    private int value; // e.g. 45%

    public TrafficDensity() {
    }
    public TrafficDensity(String crossroad, int value) {
        this.crossroad = crossroad;
        this.value = value;
    }

    public String getCrossroad() {
        return crossroad;
    }
    public void setCrossroad(String crossroad) {
        this.crossroad = crossroad;
    }

    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
}