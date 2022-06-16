package com.example.weathermanagement1.repository;

import com.example.weathermanagement1.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WeatherRepository extends JpaRepository<Weather, Long>, JpaSpecificationExecutor<Weather> {
}
