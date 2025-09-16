package com.ftn.sbnz.model.models;

public class IllegalParking {
    private String crossroad;

    public IllegalParking() {
    }
    public IllegalParking(String crossroad) {
        this.crossroad = crossroad;
    }

    public String getCrossroad() {
        return crossroad;
    }

    public void setCrossroad(String crossroad) {
        this.crossroad = crossroad;
    }
}
