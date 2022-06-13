package com.example.weathermanagement1.service;

import com.example.weathermanagement1.entity.Record;
import com.example.weathermanagement1.repository.RecordRepository;
import com.example.weathermanagement1.repository.RecordSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

@Service
public class RecordService {

    @Autowired
    private RecordRepository recordRepository;

    public Record save(Record record) {
        return recordRepository.save(record);
    }

    public List<Record> getAllRecordsByCityName(String name) {
       return recordRepository.findAll(RecordSpecification.getAllRecordsByCityName(name));
    }

    public boolean compareRecordDate(Date date) {
        return recordRepository.findAll(RecordSpecification.compareRecordDate(date)).size() > 0;
    }

}
