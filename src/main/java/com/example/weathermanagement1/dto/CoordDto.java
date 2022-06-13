package com.example.weathermanagement1.dto;

public class CoordDto {

    private double lon;

    private double lat;

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public CoordDto(double lon, double lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public CoordDto() {
    }
}
