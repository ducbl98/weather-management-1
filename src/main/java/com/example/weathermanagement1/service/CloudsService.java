package com.example.weathermanagement1.service;

import com.example.weathermanagement1.entity.Clouds;
import com.example.weathermanagement1.repository.CloudsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CloudsService {

  @Autowired private CloudsRepository cloudsRepository;

  public Clouds save(Clouds clouds) {
    return cloudsRepository.save(clouds);
  }
}
