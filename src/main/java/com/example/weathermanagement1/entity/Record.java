package com.example.weathermanagement1.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Table(name = "records")
@Entity
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "dt")
    private long dt;

    @Column(name = "visibility")
    private long visibility;

    @Column(name = "base")
    private String base;

    @Temporal(TemporalType.DATE)
    @Column(name = "measure_date")
    Date measureDate;

    @Temporal(TemporalType.TIME)
    @Column(name = "measure_time")
    Date measureTime;

    @ManyToOne
    @JoinColumn(name = "city_id", referencedColumnName = "id")
    private City city;

    @OneToOne(mappedBy = "record", cascade = CascadeType.ALL)
    private Clouds clouds;

    @OneToOne(mappedBy = "record", cascade = CascadeType.ALL)
    private Coord coordinate;

    @OneToOne(mappedBy = "record", cascade = CascadeType.ALL)
    private Main main;

    @OneToOne(mappedBy = "record", cascade = CascadeType.ALL)
    private Sys sysDetail;

    @ManyToMany(mappedBy = "records")
    private Set<Weather> weathers;

    @OneToOne(mappedBy = "record", cascade = CascadeType.ALL)
    private Wind wind;

    public Record() {
    }

    public Record(long dt, long visibility, String base,City city) {
        this.dt = dt;
        this.visibility = visibility;
        this.base = base;
        this.city = city;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public long getVisibility() {
        return visibility;
    }

    public void setVisibility(long visibility) {
        this.visibility = visibility;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Date getMeasureDate() {
        return measureDate;
    }

    public void setMeasureDate(Date measureDate) {
        this.measureDate = measureDate;
    }

    public Date getMeasureTime() {
        return measureTime;
    }

    public void setMeasureTime(Date measureTime) {
        this.measureTime = measureTime;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Coord getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coord coordinate) {
        this.coordinate = coordinate;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Sys getSysDetail() {
        return sysDetail;
    }

    public void setSysDetail(Sys sysDetail) {
        this.sysDetail = sysDetail;
    }

    public Set<Weather> getWeathers() {
        return weathers;
    }

    public void setWeathers(Set<Weather> weathers) {
        this.weathers = weathers;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

}
