package com.example.weathermanagement1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDetail {

  private String description;

  private String icon;

  @Override
  public String toString() {
    return "WeatherDetail [description=" + description + ", icon=" + icon + "]";
  }
}
