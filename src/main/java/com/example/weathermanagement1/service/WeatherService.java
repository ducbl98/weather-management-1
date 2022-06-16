package com.example.weathermanagement1.service;

import com.example.weathermanagement1.entity.Weather;
import com.example.weathermanagement1.repository.WeatherRepository;
import com.example.weathermanagement1.repository.specification.WeatherSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WeatherService {

  @Autowired private WeatherRepository weatherRepository;

  public Weather save(Weather weather) {
    Weather weather1 = weatherRepository.save(weather);
    weatherRepository.flush();
    return weather1;
  }

  public Optional<Weather> getWeatherById(Long id) {
    return weatherRepository.findOne(WeatherSpecification.getWeatherByWeatherId(id));
  }

  public void deleteWeather(Weather weather) {
    weatherRepository.delete(weather);
  }

  public List<Weather> getAllWeatherByRecordId(Long id) {
    return weatherRepository.findAll(WeatherSpecification.getAllWeatherByRecordId(id));
  }
}
