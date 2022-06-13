package com.example.weathermanagement1.entity;

import javax.persistence.*;

@Table(name = "coords")
@Entity
public class Coord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "longitude")
    private double lon;

    @Column(name = "latitude")
    private double lat;

    @OneToOne
    @JoinColumn(name = "record_id")
    private Record record;

    public Coord() {
    }

    public Coord(double lon, double lat) {
        this.lon = lon;
        this.lat = lat;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }
}
