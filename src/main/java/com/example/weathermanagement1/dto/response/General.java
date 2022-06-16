package com.example.weathermanagement1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class General {

  private double temp;

  private double feels_like;

  private double temp_min;

  private double temp_max;

  private int pressure;

  private int humidity;

  private int cloudiness;

  private long visibility;

  private WindDetail wind;

  @Override
  public String toString() {
    return "General{"
        + "temp="
        + temp
        + ", feels_like="
        + feels_like
        + ", temp_min="
        + temp_min
        + ", temp_max="
        + temp_max
        + ", pressure="
        + pressure
        + ", humidity="
        + humidity
        + ", cloudiness="
        + cloudiness
        + ", visibility="
        + visibility
        + ", wind="
        + wind
        + '}';
  }
}
