package com.example.weathermanagement1.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Table(name = "winds")
@Entity
public class Wind {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "speed")
    private double speed;

    @Column(name = "deg")
    private int deg;

    @Column(name = "gust")
    private double gust;

    @OneToOne
    @JoinColumn(name = "record_id", referencedColumnName = "id")
    private Record record;

    public Wind(double speed, int deg, double gust) {
        this.speed = speed;
        this.deg = deg;
        this.gust = gust;
    }

}
