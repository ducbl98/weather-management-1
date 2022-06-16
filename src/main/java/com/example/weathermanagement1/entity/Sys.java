package com.example.weathermanagement1.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
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

    public Sys(long sysId, String country, int sunrise, int sunset, int type) {
        this.sysId = sysId;
        this.country = country;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.type = type;
    }

}
