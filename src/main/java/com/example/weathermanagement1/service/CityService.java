package com.example.weathermanagement1.service;

import com.example.weathermanagement1.entity.City;
import com.example.weathermanagement1.repository.CityRepository;
import com.example.weathermanagement1.repository.specification.CitySpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CityService {

  @Autowired private CityRepository cityRepository;

  public City save(City city) {
    return cityRepository.save(city);
  }

  public Optional<City> findByName(String name) {
    return cityRepository.findAll(CitySpecification.findByName(name)).stream().findFirst();
  }
}
