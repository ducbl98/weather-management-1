package com.example.weathermanagement1.repository;

import com.example.weathermanagement1.entity.Coord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoordRepository extends JpaRepository<Coord, Long> {
}
