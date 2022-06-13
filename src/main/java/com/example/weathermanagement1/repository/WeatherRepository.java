package com.example.weathermanagement1.repository;

import com.example.weathermanagement1.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<Weather, Long> {
}
