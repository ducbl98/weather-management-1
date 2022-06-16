package com.example.weathermanagement1.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysDto {

    private long id;

    private String country;

    private int sunrise;

    private int sunset;

    private int type;

}
