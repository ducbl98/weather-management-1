package com.example.weathermanagement1.entity;

import javax.persistence.*;

@Table(name = "mains")
@Entity
public class Main {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "temperature")
    private double temp;

    @Column(name = "feels_like")
    private double feels_like;

    @Column(name = "temp_min")
    private double temp_min;

    @Column(name = "temp_max")
    private double temp_max;

    @Column(name = "pressure")
    private int pressure;

    @Column(name = "humidity")
    private int humidity;

    @OneToOne
    @JoinColumn(name = "record_id", referencedColumnName = "id")
    private Record record;

    public Main() {
    }

    public Main(double temp, double feels_like, double temp_min, double temp_max, int pressure, int humidity) {
        this.temp = temp;
        this.feels_like = feels_like;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.pressure = pressure;
        this.humidity = humidity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(double feels_like) {
        this.feels_like = feels_like;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(double temp_max) {
        this.temp_max = temp_max;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }
}
