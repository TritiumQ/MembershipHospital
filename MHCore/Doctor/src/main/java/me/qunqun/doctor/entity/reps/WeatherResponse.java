package me.qunqun.doctor.entity.reps;

import lombok.Data;

@Data
public class WeatherResponse {
    private double precipitation;
    private double temperature;
    private int pressure;
    private int humidity;
    private String windDirection;
    private int windDirectionDegree;
    private double windSpeed;
    private String windScale;
    private int code;
    private String place;
    private String weather1;
    private String weather2;


}