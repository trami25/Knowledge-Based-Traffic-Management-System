package com.ftn.sbnz.model.models;

public class CauseFound {
    private String crossroad;
    private String cause;

    public CauseFound() {}

    public CauseFound(String crossroad, String cause) {
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
}