package com.example.weathermanagement1.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
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

    public Clouds(int storage, Record record) {
        this.storage = storage;
        this.record = record;
    }

    public Clouds(int all) {
        this.storage = all;
    }

}
