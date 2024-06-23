package com.phorvat.weatherforecastingapp.models.forecast;

import com.phorvat.weatherforecastingapp.configuration.AuditorConfig;
import com.phorvat.weatherforecastingapp.models.forecast.request.ForecastRequest;
import com.phorvat.weatherforecastingapp.models.forecast.response.ForecastDetails;
import com.phorvat.weatherforecastingapp.models.user.User;
import com.phorvat.weatherforecastingapp.models.forecast.Forecast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/forecasts")
public class ForecastController {

    @Autowired
    private ForecastService forecastService;

    @Autowired
    private AuditorConfig auditorConfig;

    @GetMapping("")
    public List<Forecast> getAllForecasts() {
        return forecastService.getAllForecasts();
    }

    @GetMapping("/{locationId}")
    public List<Forecast> getForecastsByLocationId(@PathVariable("locationId") Integer locationId) {
        return forecastService.getForecastsByLocationId(locationId);
    }

    @GetMapping("/last/{locationId}")
    public Optional<Forecast> getLastForecastByLocationId(@PathVariable("locationId") Integer locationId) {
        return forecastService.getLatestForecastByLocationId(locationId);
    }

    @GetMapping("/next/{locationId}/{days}")
    public List<Forecast> getForecastsForNextDays(@PathVariable("locationId") Integer locationId, @PathVariable("days") Integer days) {
        if (days < 1 || days > 5) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Days parameter must be between 1 and 5");
        }
        return forecastService.getLatestForecastsForNextDays(locationId, days);
    }

    @PostMapping()
    public ForecastDetails create(@RequestBody ForecastRequest forecastRequest) {
        User user = auditorConfig
                .getCurrentAuditor()
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized"));
        return forecastService.create(forecastRequest);
    }
}

