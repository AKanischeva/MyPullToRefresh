package com.khmerlabs.mypulltorefresh.model;

public class Wind {
    private String deg;

    private String speed;

    public String getDeg() {
        return deg == null ? "Unknown" : deg;
    }

    public void setDeg(String deg) {
        this.deg = deg;
    }

    public String getSpeed() {
        return speed == null ? "Unknown" : speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "deg = " + getDeg() + ", speed = " + getSpeed();
    }
}