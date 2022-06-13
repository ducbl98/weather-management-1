package com.example.weathermanagement1.entity;

import javax.persistence.*;

@Table(name = "sys_details")
@Entity
public class Sys {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "sys_id")
    private long sysId;

    @Column(name = "country")
    private String country;

    @Column(name = "sunrise")
    private int sunrise;

    @Column(name = "sunset")
    private int sunset;

    @Column(name = "type")
    private int type;

    @OneToOne
    @JoinColumn(name = "record_id", referencedColumnName = "id")
    private Record record;

    public Sys() {
    }

    public Sys(long sysId, String country, int sunrise, int sunset, int type) {
        this.sysId = sysId;
        this.country = country;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long ident) {
        this.id = ident;
    }

    public long getSysId() {
        return sysId;
    }

    public void setSysId(long id) {
        this.sysId = id;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
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
