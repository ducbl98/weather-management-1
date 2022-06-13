package com.example.weathermanagement1.service;

import com.example.weathermanagement1.entity.Weather;
import com.example.weathermanagement1.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    @Autowired
    private WeatherRepository weatherRepository;

    public Weather save(Weather weather) {
        return weatherRepository.save(weather);
    }
}
