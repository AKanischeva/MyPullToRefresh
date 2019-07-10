package com.khmerlabs.mypulltorefresh;

public class WeatherItem {
    private Integer id;
    private String cityName;
    private String humidity;
    private String pressure;
    private String temperature;
    private String description;
    private String dateTime;
    private String wind;
    public WeatherItem(String cityName, String humidity, String pressure, String temperature, String description, String dateTime, String wind) {
        this.cityName = cityName;
        this.humidity = humidity;
        this.pressure = pressure;
        this.temperature = temperature;
        this.description = description;
        this.dateTime = dateTime;
        this.wind = wind;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }
}
