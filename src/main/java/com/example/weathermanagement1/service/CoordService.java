package com.example.weathermanagement1.service;

import com.example.weathermanagement1.entity.Coord;
import com.example.weathermanagement1.repository.CoordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoordService {

    @Autowired
    private CoordRepository coordRepository;

    public Coord save(Coord coord) {
        return coordRepository.save(coord);
    }
}
