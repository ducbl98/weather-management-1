package com.example.weathermanagement1.repository.specification;

import com.example.weathermanagement1.entity.Record;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class RecordSpecification {

    public static Specification<Record> getAllRecordsByCityName(String name) {
        if (name == null) {
            return null;
        }
        return (root, query, cb) -> {
            query.orderBy(cb.desc(root.get("measureDate")));
            return cb.equal(root.get("city").get("cityName"), name);
        };
    }

    public static Specification<Record> compareRecordDate(Date date) {
        if (date == null) {
            return null;
        }
        return (root, query, cb) -> cb.equal(root.get("measureDate"), date);
    }


}
