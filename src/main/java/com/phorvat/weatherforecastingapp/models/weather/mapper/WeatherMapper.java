package com.phorvat.weatherforecastingapp.models.weather.mapper;

import com.phorvat.weatherforecastingapp.models.location.Location;
import com.phorvat.weatherforecastingapp.models.location.LocationService;
import com.phorvat.weatherforecastingapp.models.location.response.LocationDetails;
import com.phorvat.weatherforecastingapp.models.weather.Weather;
import com.phorvat.weatherforecastingapp.models.weather.request.WeatherRequest;
import com.phorvat.weatherforecastingapp.models.weather.response.WeatherDetails;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
@Mapper(componentModel = "spring", uses = LocationService.class)
public abstract class WeatherMapper {

  @Autowired
  private LocationService locationService;

  @Mapping(target = "id", ignore = true)
  @Mapping(source = "locationId", target = "location")
  public abstract Weather toEntity(WeatherRequest weatherRequest);

  public abstract WeatherDetails toDetails(Weather weather);

  public void update(WeatherRequest weatherRequest, @MappingTarget Weather weather) {
    weather.setLocation(locationService.getLocationById(weatherRequest.getLocationId()));
  }

  @AfterMapping
  public void setLocations(WeatherRequest weatherRequest, @MappingTarget Weather weather) {
    weather.setLocation(locationService.getLocationById(weatherRequest.getLocationId()));
  }
}

