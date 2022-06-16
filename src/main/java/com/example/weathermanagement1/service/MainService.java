package com.example.weathermanagement1.service;

import com.example.weathermanagement1.entity.Main;
import com.example.weathermanagement1.repository.MainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainService {

  @Autowired private MainRepository mainRepository;

  public Main save(Main main) {
    return mainRepository.save(main);
  }
}
