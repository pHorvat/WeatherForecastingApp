package com.phorvat.weatherforecastingapp.models.forecast.response;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ForecastDetails {
    private Integer locationId;
    private java.sql.Date forecastDate;
    private Float tempMax;
    private Float tempMin;
    private Float chanceOfRain;
    private Timestamp forecastTimestamp;
    private Integer conditionCode;
    private String conditions;
}
