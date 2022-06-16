package com.example.weathermanagement1.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MainDto {

    private double temp;

    private double feels_like;

    private double temp_min;

    private double temp_max;

    private int pressure;

    private int humidity;

}
