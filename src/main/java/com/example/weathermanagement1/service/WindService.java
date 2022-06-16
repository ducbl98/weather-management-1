package com.example.weathermanagement1.service;

import com.example.weathermanagement1.entity.Wind;
import com.example.weathermanagement1.repository.WindRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WindService {

  @Autowired private WindRepository windRepository;

  public Wind save(Wind wind) {
    return windRepository.save(wind);
  }
}
