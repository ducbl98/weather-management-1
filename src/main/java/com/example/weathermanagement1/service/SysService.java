package com.example.weathermanagement1.service;

import com.example.weathermanagement1.entity.Sys;
import com.example.weathermanagement1.repository.SysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysService {

    @Autowired
    private SysRepository sysRepository;

    public Sys save(Sys sys) {
        return sysRepository.save(sys);
    }
}
