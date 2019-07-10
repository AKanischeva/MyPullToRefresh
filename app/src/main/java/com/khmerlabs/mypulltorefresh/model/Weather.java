package com.khmerlabs.mypulltorefresh.model;

public class Weather {
    private String icon;

    private String description;

    private String main;

    private String id;

    public String getIcon() {
        return icon == null ? "Unknown" : icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description == null ? "Unknown" : description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMain() {
        return main == null ? "Unknown" : main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getId() {
        return id == null ? "Unknown" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "[icon = " + getIcon() + ", description = " + getDescription() + ", main = " + getMain() + ", id = " + getId() + "]";
    }
}