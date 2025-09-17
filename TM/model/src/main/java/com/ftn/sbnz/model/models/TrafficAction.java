package com.ftn.sbnz.model.models;

public class TrafficAction {
    private String crossroad;
    private String action; // e.g. "Increase green light duration"
    private Object value;

    public TrafficAction() {
    }
    public TrafficAction(String crossroad, String action) {
        this.crossroad = crossroad;
        this.action = action;
    }

    public TrafficAction(String crossroad, String action, Object value) {
        this.crossroad = crossroad;
        this.action = action;
        this.value = value;
    }

    public String getCrossroad() {
        return crossroad;
    }
    public void setCrossroad(String crossroad) {
        this.crossroad = crossroad;
    }

    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
