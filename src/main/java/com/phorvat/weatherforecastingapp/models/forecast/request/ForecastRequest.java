package com.phorvat.weatherforecastingapp.models.forecast.request;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
public class ForecastRequest {
    private Integer locationId;
    private LocalDate forecastDate;
    private Float tempMax;
    private Float tempMin;
    private Float chanceOfRain;
    private Timestamp forecastTimestamp;
    private Integer conditionCode;
    private String conditions;
}
