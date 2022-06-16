package com.example.weathermanagement1.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
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

    public Main(double temp, double feels_like, double temp_min, double temp_max, int pressure, int humidity) {
        this.temp = temp;
        this.feels_like = feels_like;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.pressure = pressure;
        this.humidity = humidity;
    }

}
