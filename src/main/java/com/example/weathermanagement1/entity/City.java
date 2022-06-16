package com.example.weathermanagement1.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
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

    public City(long cityId, long cityCod, String cityName, int timezone) {
        this.cityId = cityId;
        this.cityCod = cityCod;
        this.cityName = cityName;
        this.timezone = timezone;
    }



}
