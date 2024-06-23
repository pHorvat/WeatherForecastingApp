package com.phorvat.weatherforecastingapp.models.forecast;

import com.phorvat.weatherforecastingapp.models.forecast.Forecast;
import com.phorvat.weatherforecastingapp.models.forecast.mapper.ForecastMapper;
import com.phorvat.weatherforecastingapp.models.forecast.request.ForecastRequest;
import com.phorvat.weatherforecastingapp.models.forecast.response.ForecastDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ForecastService {

    private final ForecastRepository forecastRepository;
    private final ForecastMapper forecastMapper;

    public List<Forecast> getAllForecasts() {
        return forecastRepository.findAll();
    }

    public List<Forecast> getForecastsByLocationId(Integer locationId) {
        return forecastRepository.findByLocationId(locationId);
    }

    public Optional<Forecast> getLatestForecastByLocationId(Integer locationId) {
        return forecastRepository.findFirstByLocationIdOrderByForecastTimestampDesc(locationId);
    }

    public List<Forecast> getLatestForecastsForNextDays(Integer locationId, Integer days) {
        return forecastRepository.findLatestForecastsForNextDays(locationId, days);
    }

    public ForecastDetails create(ForecastRequest forecastRequest) {
        Forecast forecast = forecastMapper.toEntity(forecastRequest);
        return forecastMapper.toDetails(forecastRepository.save(forecast));
    }
}

