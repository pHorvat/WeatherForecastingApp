package com.phorvat.weatherforecastingapp.models.forecast.response;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDate;

@Data
public class ForecastDetails {
    private Integer locationId;
    private LocalDate forecastDate;
    private Float tempMax;
    private Float tempMin;
    private Float chanceOfRain;
    private Timestamp forecastTimestamp;
    private Integer conditionCode;
    private String conditions;
}
