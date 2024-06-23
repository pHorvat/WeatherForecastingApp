package com.phorvat.weatherforecastingapp.models.forecast;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.phorvat.weatherforecastingapp.models.location.Location;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "forecasts")
@Data
public class Forecast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate forecast_date;

    private Float temp_max;

    private Float temp_min;

    private Float chance_of_rain;

    private Timestamp forecast_timestamp;

    private Integer condition_code;

    private String conditions;

    @ManyToOne
    @JoinColumn(name = "location_id")
    @JsonIgnore
    private Location location;
}
