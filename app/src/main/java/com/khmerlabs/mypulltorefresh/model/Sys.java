package com.khmerlabs.mypulltorefresh.model;

public class Sys {
    private String country;

    private String sunrise;

    private String sunset;

    private String id;

    private String type;

    private String message;

    public String getCountry() {
        return country == null ? "Unknown" : country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSunrise() {
        return sunrise == null ? "Unknown" : sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset == null ? "Unknown" : sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getId() {
        return id == null ? "Unknown" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type == null ? "Unknown" : type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message == null ? "Unknown" : message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "[country = " + getCountry() + ", sunrise = " + getSunrise() + ", sunset = " + getSunset() + ", id = " + getId() + ", type = " + getType() + ", message = " + getMessage() + "]";
    }
}
