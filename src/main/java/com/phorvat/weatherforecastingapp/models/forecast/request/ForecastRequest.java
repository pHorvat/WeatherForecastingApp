package com.phorvat.weatherforecastingapp.models.forecast.request;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ForecastRequest {
    private Integer locationId;
    private java.sql.Date forecastDate;
    private Float tempMax;
    private Float tempMin;
    private Float chanceOfRain;
    private Timestamp forecastTimestamp;
    private Integer conditionCode;
    private String conditions;
}
