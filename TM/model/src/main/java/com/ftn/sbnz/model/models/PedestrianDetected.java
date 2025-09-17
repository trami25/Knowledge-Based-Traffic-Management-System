package com.ftn.sbnz.model.models;

public class PedestrianDetected {
    private String crossroad;
    private int count; // number of pedestrians detected

    public PedestrianDetected() {
    }
    public PedestrianDetected(String crossroad, int count) {
        this.crossroad = crossroad;
        this.count = count;
    }

    public String getCrossroad() {
        return crossroad;
    }
    public void setCrossroad(String crossroad) {
        this.crossroad = crossroad;
    }

    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
}
