package com.phorvat.weatherforecastingapp.scheduler;

import com.phorvat.weatherforecastingapp.models.location.Location;
import com.phorvat.weatherforecastingapp.models.location.LocationRepository;
import com.phorvat.weatherforecastingapp.models.weather.Weather;
import com.phorvat.weatherforecastingapp.models.weather.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class GlobalScheduler {

  private static final String WEATHER_API_URL = "http://api.weatherapi.com/v1/current.json";
  private static final String API_KEY = "91bfa226a05d42c0bbe144344242206";

  private final RestTemplate restTemplate;
  private final LocationRepository locationRepository;
  private final WeatherRepository weatherRepository;

  @Scheduled(fixedRate = 600000)  // 10 minutes
  public void fetchWeatherData() {
    List<Location> locations = locationRepository.findAll();

    locations.forEach(location -> {
      String url = UriComponentsBuilder.fromHttpUrl(WEATHER_API_URL)
              .queryParam("key", API_KEY)
              .queryParam("q", location.getName())
              .toUriString();

      Map<String, Object> response = restTemplate.getForObject(url, Map.class);
      if (response != null) {
        Map<String, Object> current = (Map<String, Object>) response.get("current");
        if (current != null) {
          Weather weather = new Weather();
          weather.setLocation(location);
          weather.setTemperature(((Number) current.get("temp_c")).floatValue());
          weather.setHumidity(((Number) current.get("humidity")).floatValue());
          weather.setPrecipitation(((Number) current.get("precip_mm")).floatValue());
          weather.setConditions(((Map<String, String>) current.get("condition")).get("text"));
          weather.setCondition_code(((Map<String, Integer>) current.get("condition")).get("code"));
          weather.setTimestamp(new Timestamp(((Number) current.get("last_updated_epoch")).longValue() * 1000));

          weatherRepository.save(weather);
          System.out.println("Saved weather data for " + location.getName());
        }
      }
    });
  }
}
