package hr.algebra.travelplanner.feature.trip;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hr.algebra.travelplanner.feature.customer.Customer;
import hr.algebra.travelplanner.feature.destination.Destination;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "trips")
@Data
public class Trip {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;

  private LocalDate startDate;

  private LocalDate endDate;

  @OneToMany(mappedBy = "trip", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Destination> destinations;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  @JsonIgnore
  private Customer customer;
}
