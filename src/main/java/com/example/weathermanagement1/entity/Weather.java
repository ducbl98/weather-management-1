package com.example.weathermanagement1.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany
    @JoinTable(name = "weathers_records",
            joinColumns = @JoinColumn(name = "weather_id"),
            inverseJoinColumns = @JoinColumn(name = "record_id"))
    private Set<Record> records = new HashSet<>();

    public Weather() {
    }

    public Weather(long weatherId, String main, String description, String icon) {
        this.weatherId = weatherId;
        this.main = main;
        this.description = description;
        this.icon = icon;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(long id) {
        this.weatherId = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Set<Record> getRecords() {
        return records;
    }

    public void setRecords(Set<Record> records) {
        this.records = records;
    }
}
