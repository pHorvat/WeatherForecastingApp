package com.phorvat.weatherforecastingapp.scheduler;

import com.phorvat.weatherforecastingapp.models.forecast.Forecast;
import com.phorvat.weatherforecastingapp.models.forecast.ForecastDTO;
import com.phorvat.weatherforecastingapp.models.forecast.ForecastRepository;
import com.phorvat.weatherforecastingapp.models.location.Location;
import com.phorvat.weatherforecastingapp.models.location.LocationRepository;
import com.phorvat.weatherforecastingapp.models.weather.Weather;
import com.phorvat.weatherforecastingapp.models.weather.WeatherDTO;
import com.phorvat.weatherforecastingapp.models.weather.WeatherRepository;
import com.phorvat.weatherforecastingapp.utils.HttpClient;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GlobalScheduler {

  private final LocationRepository locationRepository;
  private final WeatherRepository weatherRepository;
  private final ForecastRepository forecastRepository;
  private final HttpClient httpClient;
  private static final String API_KEY = "91bfa226a05d42c0bbe144344242206"; // Extracted API key

  @Scheduled(fixedRate = 3600000) // Run every hour
  public void fetchWeatherData() {
    List<Location> locations = locationRepository.findAll();

    for (Location location : locations) {
      try {
        // Fetch combined weather and forecast data
        String url = String.format("http://api.weatherapi.com/v1/forecast.json?key=%s&q=%s&days=5&hour=12", API_KEY, location.getName());
        String jsonResponse = httpClient.get(url, String.class);

        // Map to WeatherDTO
        WeatherDTO weatherDTO = httpClient.parseJson(jsonResponse, "current", WeatherDTO.class);
        if (weatherDTO != null) {
          saveWeatherData(weatherDTO, location);
        }

        // Map to ForecastDTO
        ForecastDTO forecastDTO = httpClient.parseJson(jsonResponse, "forecast", ForecastDTO.class);
        if (forecastDTO != null) {
          saveForecastData(forecastDTO, location);
        }
      } catch (Exception e) {
        System.err.printf("Error fetching data for location %s: %s%n", location.getName(), e.getMessage());
      }
    }
  }

  private void saveWeatherData(WeatherDTO weatherDTO, Location location) {
    Weather weather = new Weather();
    weather.setLocation(location);
    weather.setTemperature(weatherDTO.getTempC());
    weather.setHumidity(weatherDTO.getHumidity());
    weather.setPrecipitation(weatherDTO.getPrecipMm());
    weather.setConditions(weatherDTO.getCondition().getText());
    weather.setCondition_code(weatherDTO.getCondition().getCode());
    weather.setTimestamp(new Timestamp(weatherDTO.getLastUpdatedEpoch() * 1000L));
    weatherRepository.save(weather);
  }

  private void saveForecastData(ForecastDTO forecastDTO, Location location) {
    List<ForecastDTO.ForecastDayDTO> forecastDays = forecastDTO.getForecastDays();
    if (forecastDays != null) {
      for (ForecastDTO.ForecastDayDTO forecastDay : forecastDays) {
        if (forecastDay != null && forecastDay.getDay() != null && forecastDay.getDay().getCondition() != null) {
          Forecast forecast = new Forecast();
          forecast.setLocation(location);
          forecast.setForecast_date(LocalDate.parse(forecastDay.getDate()));
          forecast.setTemp_max(forecastDay.getDay().getMaxTempC());
          forecast.setTemp_min(forecastDay.getDay().getMinTempC());
          forecast.setChance_of_rain((float) forecastDay.getDay().getDailyChanceOfRain());
          forecast.setCondition_code(forecastDay.getDay().getCondition().getCode());
          forecast.setConditions(forecastDay.getDay().getCondition().getText());
          forecast.setForecast_timestamp(Timestamp.valueOf(LocalDateTime.now()));
          forecastRepository.save(forecast);
        }
      }
    }
  }
}