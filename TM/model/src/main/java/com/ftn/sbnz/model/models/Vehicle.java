package com.ftn.sbnz.model.models;

public class Vehicle {

    private String crossroad;
    public double speed;
    private String type; // e.g. car, truck, motorcycle

    public Vehicle() {
    }

    public Vehicle(String crossroad, double speed, String type) {
        this.crossroad = crossroad;
        this.speed = speed;
        this.type = type;
    }

    public String getCrossroad() {
        return crossroad;
    }

    public void setCrossroad(String crossroad) {
        this.crossroad = crossroad;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
