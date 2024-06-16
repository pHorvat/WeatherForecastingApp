package com.phorvat.weatherforecastingapp.models.location.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LocationRequest {
  private String name;
  private double latitude;
  private double longitude;
  private String country;
}
