package com.example.weathermanagement1.dto;

public class CloudsDto {
    private int all;

    public CloudsDto(int all) {
        this.all = all;
    }

    public CloudsDto() {
    }

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }
}
