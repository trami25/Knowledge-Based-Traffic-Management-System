package com.ftn.sbnz.model.models;

public class EventDay {
    private String eventType; // e.g. concert, sports event
    private int expectedTraffic; // e.g. 2000 vehicles

    public EventDay() {
    }

    public EventDay(String eventType, int expectedTraffic) {
        this.eventType = eventType;
        this.expectedTraffic = expectedTraffic;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getExpectedTraffic() {
        return expectedTraffic;
    }

    public void setExpectedTraffic(int expectedTraffic) {
        this.expectedTraffic = expectedTraffic;
    }
}
