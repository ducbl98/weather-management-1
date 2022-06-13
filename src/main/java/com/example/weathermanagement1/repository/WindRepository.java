package com.example.weathermanagement1.repository;

import com.example.weathermanagement1.entity.Wind;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WindRepository extends JpaRepository<Wind, Long> {
}
