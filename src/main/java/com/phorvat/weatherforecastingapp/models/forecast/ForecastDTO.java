package com.phorvat.weatherforecastingapp.models.forecast;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ForecastDTO {

    @JsonProperty("forecastday")
    private List<ForecastDayDTO> forecastDays;

    @Data
    public static class ForecastDayDTO {

        @JsonProperty("date")
        private String date;

        @JsonProperty("day")
        private DayDTO day;

        @Data
        public static class DayDTO {

            @JsonProperty("maxtemp_c")
            private float maxTempC;

            @JsonProperty("mintemp_c")
            private float minTempC;

            @JsonProperty("daily_chance_of_rain")
            private int dailyChanceOfRain;

            @JsonProperty("condition")
            private ConditionDTO condition;

            @Data
            public static class ConditionDTO {

                @JsonProperty("code")
                private int code;

                @JsonProperty("text")
                private String text;
            }
        }
    }
}