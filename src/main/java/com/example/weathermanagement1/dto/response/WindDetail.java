package com.example.weathermanagement1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WindDetail {

  private double speed;

  private int deg;

  private double gust;

  @Override
  public String toString() {
    return "WindDetail [speed=" + speed + ", deg=" + deg + ", gust=" + gust + "]";
  }

}
