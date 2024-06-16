package hr.algebra.travelplanner.feature.destination;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hr.algebra.travelplanner.feature.country.Country;
import hr.algebra.travelplanner.feature.location.Location;
import hr.algebra.travelplanner.feature.trip.Trip;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "destinations")
@Data
public class Destination {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "trip_id")
  @JsonIgnore
  private Trip trip;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "country_id")
  private Country country;

  @OneToMany(mappedBy = "destination", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Location> locations;
}
