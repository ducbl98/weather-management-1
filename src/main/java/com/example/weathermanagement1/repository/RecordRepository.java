package com.example.weathermanagement1.repository;

import com.example.weathermanagement1.entity.City;
import com.example.weathermanagement1.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RecordRepository extends JpaRepository<Record, Long>, JpaSpecificationExecutor<Record> {
}
