package com.example.weathermanagement1.entity;

import javax.persistence.*;

@Table(name = "clouds_details")
@Entity
public class Clouds {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "storage")
    private int storage;

    @OneToOne
    @JoinColumn(name = "record_id", referencedColumnName = "id")
    private Record record;

    public Clouds() {
    }

    public Clouds(int storage, Record record) {
        this.storage = storage;
        this.record = record;
    }

    public Clouds(int all) {
        this.storage = all;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int all) {
        this.storage = all;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }
}
