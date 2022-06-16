package com.example.weathermanagement1.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Table(name = "weathers")
@Entity
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "weather_id")
    private long weatherId;

    @Column(name = "main")
    private String main;

    @Column(name = "description")
    private String description;

    @Column(name = "icon")
    private String icon;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "weathers_records",
            joinColumns = @JoinColumn(name = "weather_id"),
            inverseJoinColumns = @JoinColumn(name = "record_id"))
    private Set<Record> records = new HashSet<>();

    public Weather(long weatherId, String main, String description, String icon) {
        this.weatherId = weatherId;
        this.main = main;
        this.description = description;
        this.icon = icon;
    }

//    @Override
//   public String toString() {
//        return "Weather{" +
//                "id=" + id +
//                 ", weatherId=" + weatherId +
//                ", main='" + main + '\'' +
//                ", description='" + description + '\'' +
//                ", icon='" + icon + '\'' +
//                '}';
//    }
}
