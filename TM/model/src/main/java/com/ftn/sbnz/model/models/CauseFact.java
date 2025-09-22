package com.ftn.sbnz.model.models;

public class CauseFact {
    private String crossroad;
    private String cause;

    public CauseFact() {}

    public CauseFact(String crossroad, String cause) {
        this.crossroad = crossroad;
        this.cause = cause;
    }

    public String getCrossroad() {
        return crossroad;
    }

    public void setCrossroad(String crossroad) {
        this.crossroad = crossroad;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    @Override
    public String toString() {
        return "CauseFact{" + "crossroad='" + crossroad + '\'' + ", cause='" + cause + '\'' + '}';
    }
}
