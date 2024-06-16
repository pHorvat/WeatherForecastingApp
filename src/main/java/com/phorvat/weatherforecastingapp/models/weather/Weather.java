package com.phorvat.weatherforecastingapp.models.weather;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.phorvat.weatherforecastingapp.models.location.Location;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "weathers")
@Data
public class Weather {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private Float temperature;

  private Float humidity;

  private Float precipitation;

  private String conditions;

  private Timestamp timestamp;

  @ManyToOne
  @JoinColumn(name = "location_id")
  private Location location;

}
