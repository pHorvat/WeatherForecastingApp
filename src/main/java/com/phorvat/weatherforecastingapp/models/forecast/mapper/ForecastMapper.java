package com.phorvat.weatherforecastingapp.models.forecast.mapper;

import com.phorvat.weatherforecastingapp.models.forecast.Forecast;
import com.phorvat.weatherforecastingapp.models.forecast.request.ForecastRequest;
import com.phorvat.weatherforecastingapp.models.forecast.response.ForecastDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ForecastMapper {

    Forecast toEntity(ForecastRequest forecastRequest);

    ForecastDetails toDetails(Forecast forecast);
}
