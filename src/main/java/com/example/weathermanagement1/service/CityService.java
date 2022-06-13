package com.example.weathermanagement1.service;

import com.example.weathermanagement1.entity.City;
import com.example.weathermanagement1.entity.Record;
import com.example.weathermanagement1.repository.CityRepository;
import com.example.weathermanagement1.repository.CitySpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    public City save(City city) {
        return cityRepository.save(city);
    }

    public Optional<City> findByName(String name) {
        return cityRepository.findAll(CitySpecification.findByName(name)).stream().findFirst();
    }

//    public Set<Record> getAllRecordsByCityName(String name) {
//        return cityRepository.findAll(CitySpecification.getLatestRecord(name)).get(0).getRecords();
//    }

}
