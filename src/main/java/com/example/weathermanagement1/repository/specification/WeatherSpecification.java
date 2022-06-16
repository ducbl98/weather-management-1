package com.example.weathermanagement1.repository.specification;

import com.example.weathermanagement1.entity.Record;
import com.example.weathermanagement1.entity.Weather;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

public class WeatherSpecification {

    public static Specification<Weather> getWeatherByWeatherId(Long id) {
        if (id == null) {
            return null;
        }
        return (root, query, cb) -> cb.equal(root.get("weatherId"), id);
    }

    public static Specification<Weather> getAllWeatherByRecordId(Long id) {
        if (id == null) {
            return null;
        }
        return (root, query, cb) -> {
            query.isDistinct();
            Root<Weather> weatherRoot = root;
            Root<Record> recordRoot = query.from(Record.class);
            Expression<List<Weather>> RecordWeathers = recordRoot.get("weathers");
            return cb.and(cb.equal(recordRoot.get("id"), id), cb.isMember(weatherRoot, RecordWeathers));
        };
    }
}
