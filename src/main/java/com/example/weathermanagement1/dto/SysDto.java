package com.example.weathermanagement1.dto;

public class SysDto {

    private long id;

    private String country;

    private int sunrise;

    private int sunset;

    private int type;

    public SysDto() {
    }

    public SysDto(long id, String country, int sunrise, int sunset, int type) {
        this.id = id;
        this.country = country;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getSunrise() {
        return sunrise;
    }

    public void setSunrise(int sunrise) {
        this.sunrise = sunrise;
    }

    public int getSunset() {
        return sunset;
    }

    public void setSunset(int sunset) {
        this.sunset = sunset;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
