package com.phorvat.weatherforecastingapp.models.weather.request;

import com.phorvat.weatherforecastingapp.models.location.Location;
import com.phorvat.weatherforecastingapp.models.location.request.LocationRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class WeatherRequest {
  private Float temperature;
  private Float humidity;
  private Float precipitation;
  private Float conditions;
  private Integer locationId;
  private Integer condition_code;
  private Timestamp timestamp;
}
