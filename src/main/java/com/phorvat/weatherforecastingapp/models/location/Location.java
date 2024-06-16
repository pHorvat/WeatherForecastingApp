package com.phorvat.weatherforecastingapp.models.location;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.phorvat.weatherforecastingapp.models.user.User;
import com.phorvat.weatherforecastingapp.models.weather.Weather;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

  /*
  @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  private List<Weather> weathers;

  @Override
  public String toString() {
    return "Location{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", latitude=" + latitude +
            ", longitude=" + longitude +
            ", country='" + country + '\'' +
            '}';
  }
*/
}
