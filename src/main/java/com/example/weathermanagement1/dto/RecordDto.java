package com.example.weathermanagement1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RecordDto {

    private long dt;

    private long visibility;

    private String base;

    private long id;

    private int cod;

    private String name;

    private int timezone;

    private CoordDto coord;

    private CloudsDto clouds;

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public long getVisibility() {
        return visibility;
    }

    public void setVisibility(long visibility) {
        this.visibility = visibility;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimezone() {
        return timezone;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    public CoordDto getCoord() {
        return coord;
    }

    public void setCoord(CoordDto coord) {
        this.coord = coord;
    }

    public CloudsDto getClouds() {
        return clouds;
    }

    public void setClouds(CloudsDto clouds) {
        this.clouds = clouds;
    }

    public SysDto getSys() {
        return sys;
    }

    public void setSys(SysDto sys) {
        this.sys = sys;
    }

    public Set<WeatherDto> getWeather() {
        return weather;
    }

    public void setWeather(Set<WeatherDto> weather) {
        this.weather = weather;
    }

    public WindDto getWind() {
        return wind;
    }

    public void setWind(WindDto wind) {
        this.wind = wind;
    }

    public MainDto getMain() {
        return main;
    }

    public void setMain(MainDto main) {
        this.main = main;
    }

    private SysDto sys;

    private Set<WeatherDto> weather;

    private WindDto wind;

    private MainDto main;

}
