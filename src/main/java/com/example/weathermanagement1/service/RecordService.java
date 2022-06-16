package com.example.weathermanagement1.service;

import com.example.weathermanagement1.entity.Record;
import com.example.weathermanagement1.repository.RecordRepository;
import com.example.weathermanagement1.repository.specification.RecordSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RecordService {

  @Autowired private RecordRepository recordRepository;

  public Record save(Record record) {
    Record record1 = recordRepository.save(record);
    recordRepository.flush();
    return record1;
  }

  public List<Record> getAllRecordsByCityName(String name) {
    return recordRepository.findAll(RecordSpecification.getAllRecordsByCityName(name));
  }

  public boolean compareRecordDate(Date date) {
    return !recordRepository.findAll(RecordSpecification.compareRecordDate(date)).isEmpty();
  }

  public Record getRecordById(Long id) {
    Optional<Record> record = recordRepository.findById(id);
    if (record.isEmpty()) {
      throw new IllegalArgumentException("Record not found");
    }
    return record.get();
  }

  public String delete(Long id) {
    Optional<Record> record = recordRepository.findById(id);
    if (record.isPresent()) {
      recordRepository.delete(record.get());
      return "Delete successfully";
    }
    return "Delete failed";
  }

}
