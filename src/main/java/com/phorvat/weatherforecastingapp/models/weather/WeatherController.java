package com.phorvat.weatherforecastingapp.models.weather;

import com.phorvat.weatherforecastingapp.configuration.AuditorConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/weather")
public class WeatherController {
  @Autowired private WeatherService weatherService;

    @GetMapping("")
  public List<Weather> getAllDWeathers() {
    return weatherService.getAllWeathers();
  }

  @GetMapping("/{locationId}")
  public List<Weather> getWeatherByLocationId(@PathVariable("locationId") Integer locationId) {
    return weatherService.getWeatherByLocationId(locationId);
  }

  @GetMapping("/last/{locationId}")
  public Optional<Weather> getLastWeatherByLocationId(@PathVariable("locationId") Integer locationId) {
    return weatherService.getLatestWeatherByLocationId(locationId);
  }

}
