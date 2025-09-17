package com.ftn.sbnz.model.models;

public class TimeOfDay {
    private int hour;

    public TimeOfDay() {}

    public TimeOfDay(int hour) {
        this.hour = hour;
    }

    public int getHour() { return hour; }
    public void setHour(int hour) { this.hour = hour; }
}
