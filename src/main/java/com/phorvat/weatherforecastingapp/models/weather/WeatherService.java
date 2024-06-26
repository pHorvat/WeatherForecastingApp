package com.phorvat.weatherforecastingapp.models.weather;


import com.phorvat.weatherforecastingapp.models.weather.mapper.WeatherMapper;
import com.phorvat.weatherforecastingapp.models.weather.request.WeatherRequest;
import com.phorvat.weatherforecastingapp.models.weather.response.WeatherDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeatherService {

  private final WeatherRepository weatherRepository;
  private final WeatherMapper weatherMapper;


  public List<Weather> getAllWeathers() {
    return weatherRepository.findAll();
  }

  public List<Weather> getWeatherByLocationId(Integer locationId) {
    return weatherRepository.findByLocationId(locationId);
  }

  public Optional<Weather> getLatestWeatherByLocationId(Integer locationId) {
    return weatherRepository.findFirstByLocationIdOrderByTimestampDesc(locationId);
  }

  public WeatherDetails create(WeatherRequest weatherRequest) {
    if (weatherRequest.getTimestamp() == null) {
      weatherRequest.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
    }
    Weather weather = weatherMapper.toEntity(weatherRequest);
    return weatherMapper.toDetails(weatherRepository.save(weather));
  }

}
