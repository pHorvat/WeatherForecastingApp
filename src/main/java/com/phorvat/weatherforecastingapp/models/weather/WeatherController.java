package com.phorvat.weatherforecastingapp.models.weather;

import com.phorvat.weatherforecastingapp.configuration.AuditorConfig;
import com.phorvat.weatherforecastingapp.models.user.User;
import com.phorvat.weatherforecastingapp.models.weather.request.WeatherRequest;
import com.phorvat.weatherforecastingapp.models.weather.response.WeatherDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/weather")
public class WeatherController {
  @Autowired private WeatherService weatherService;
  @Autowired private AuditorConfig auditorConfig;

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

  @PostMapping()
  public WeatherDetails create(@RequestBody WeatherRequest weatherRequest) {
    User user =
            auditorConfig
                    .getCurrentAuditor()
                    .orElseThrow(
                            () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized"));
    return weatherService.create(weatherRequest);
  }
}
