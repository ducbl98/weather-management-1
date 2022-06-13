package com.example.weathermanagement1.entity;

import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "cities")
@Entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "city_id")
    private long cityId;

    @Column(name = "city_cod")
    private long cityCod;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "timezone")
    private int timezone;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private Set<Record> records;

    public City() {
    }

    public City(long cityId, long cityCod, String cityName, int timezone) {
        this.cityId = cityId;
        this.cityCod = cityCod;
        this.cityName = cityName;
        this.timezone = timezone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCityId() {
        return cityId;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    public long getCityCod() {
        return cityCod;
    }

    public void setCityCod(long cityCod) {
        this.cityCod = cityCod;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getTimezone() {
        return timezone;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    public Set<Record> getRecords() {
        return records;
    }

    public void setRecords(Set<Record> records) {
        this.records = records;
    }
}
