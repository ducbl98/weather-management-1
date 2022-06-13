package com.example.weathermanagement1.repository;

import com.example.weathermanagement1.entity.Clouds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CloudsRepository extends JpaRepository<Clouds, Long> {
}
