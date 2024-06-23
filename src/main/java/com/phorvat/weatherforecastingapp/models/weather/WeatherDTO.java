package com.phorvat.weatherforecastingapp.models.weather;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WeatherDTO {

    @JsonProperty("last_updated_epoch")
    private long lastUpdatedEpoch;

    @JsonProperty("temp_c")
    private float tempC;

    @JsonProperty("humidity")
    private float humidity;

    @JsonProperty("precip_mm")
    private float precipMm;

    @JsonProperty("condition")
    private ConditionDTO condition;

    @Data
    public static class ConditionDTO {
        @JsonProperty("text")
        private String text;

        @JsonProperty("code")
        private int code;
    }
}