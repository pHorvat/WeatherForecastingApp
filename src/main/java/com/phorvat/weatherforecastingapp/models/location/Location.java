package com.phorvat.weatherforecastingapp.models.location;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "locations")
@Data
public class Location {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;

  private double latitude;

  private double longitude;

  private String country;

}
