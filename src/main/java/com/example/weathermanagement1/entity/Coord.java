package com.example.weathermanagement1.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
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

    public Coord(double lon, double lat) {
        this.lon = lon;
        this.lat = lat;
    }

}
