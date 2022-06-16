package com.example.weathermanagement1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDataResponse {

  private long id;
  private long cityId;

  private String cityName;

  private String countryCode;

  private int timezone;

  private String measureTime;

  private List<WeatherDetail> weathers;

  private General general;

  public WeatherDataResponse(
      long id,
      long cityId,
      String cityName,
      String countryCode,
      int timezone,
      List<WeatherDetail> weathers,
      General general,
      String measureTime) {
    this.id = id;
    this.cityId = cityId;
    this.cityName = cityName;
    this.countryCode = countryCode;
    this.timezone = timezone;
    this.weathers = weathers;
    this.general = general;
    this.measureTime = measureTime;
  }

  @Override
  public String toString() {
    return "WeatherDataResponse{"
        + "cityId="
        + cityId
        + ", cityName="
        + cityName
        + ", countryCode="
        + countryCode
        + ", timezone="
        + timezone
        + ", weathers="
        + weathers
        + ", general="
        + general
        + '}';
  }
}
