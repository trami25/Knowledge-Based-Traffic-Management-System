package com.ftn.sbnz.model.models;

public class EmergencyVehicle {
    private String location;
    private String direction; // e.g. north, south, east, west

    public EmergencyVehicle() {
    }
    public EmergencyVehicle(String location, String direction) {
        this.location = location;
        this.direction = direction;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
