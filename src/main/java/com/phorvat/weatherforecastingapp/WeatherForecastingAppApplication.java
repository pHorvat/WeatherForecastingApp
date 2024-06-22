package com.phorvat.weatherforecastingapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WeatherForecastingAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherForecastingAppApplication.class, args);
    }

}
