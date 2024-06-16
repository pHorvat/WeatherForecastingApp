package com.phorvat.weatherforecastingapp.models.weather.response;

import com.phorvat.weatherforecastingapp.models.location.response.LocationDetails;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class WeatherDetails {
    private Integer id;
    private Float temperature;
    private Float humidity;
    private Float precipitation;
    private Float conditions;
    private LocationDetails location;
    private Timestamp timestamp;
}
