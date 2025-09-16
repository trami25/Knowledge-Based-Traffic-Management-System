package com.ftn.sbnz.model.models;

import java.util.List;

public class Crossroad {
    private String id;
    private List<String> connectedRoads;

    public Crossroad() {
    }

    public Crossroad(String id, List<String> connectedRoads) {
        this.id = id;
        this.connectedRoads = connectedRoads;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getConnectedRoads() {
        return connectedRoads;
    }

    public void setConnectedRoads(List<String> connectedRoads) {
        this.connectedRoads = connectedRoads;
    }
}
