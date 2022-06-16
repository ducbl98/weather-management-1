package com.example.weathermanagement1.repository.specification;

import com.example.weathermanagement1.entity.City;
import com.example.weathermanagement1.entity.Record;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

public class CitySpecification {

    public static Specification<City> findByName(String name) {
        if (name == null) {
            return null;
        }
        return (root, query, cb) -> cb.equal(root.get("cityName"), name);
    }



    public static Specification<City> getLatestRecord(String cityName) {
        if (cityName == null) {
            return null;
        }
        return (root, query, cb) -> {
            Join<City, Record> recordJoin = root.join("records");
            query.orderBy(cb.desc(recordJoin.get("measureDate")));
            return cb.equal(root.get("cityName"), cityName);
        };
    }

}
