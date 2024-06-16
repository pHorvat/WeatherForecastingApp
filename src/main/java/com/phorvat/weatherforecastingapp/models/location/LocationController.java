package hr.algebra.travelplanner.feature.trip;

import hr.algebra.travelplanner.authentication.jwt.JwtService;
import hr.algebra.travelplanner.feature.customer.Customer;
import hr.algebra.travelplanner.feature.trip.request.TripRequest;
import hr.algebra.travelplanner.feature.trip.response.TripDetails;
import hr.algebra.travelplanner.configuration.AuditorConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/trips")
public class TripController {
  @Autowired private TripService tripService;
  @Autowired private AuditorConfig auditorConfig;

  @GetMapping()
  public List<TripDetails> getAllTrips() {
    return tripService.getAllTrips();
  }

  @GetMapping("/my-trips")
  public List<TripDetails> getAllUserTripsSimple() {
    Customer customer =
        auditorConfig
            .getCurrentAuditor()
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized"));
    return tripService.getAllUserTrips(customer.getId());
  }

  @PostMapping()
  public TripDetails create(@RequestBody TripRequest tripRequest) {
    Customer customer =
        auditorConfig
            .getCurrentAuditor()
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized"));
    return tripService.create(customer, tripRequest);
  }

  @PutMapping("/{id}")
  public TripDetails update(@PathVariable Integer id, @RequestBody TripRequest tripRequest) {
    return tripService.update(id, tripRequest);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Integer id) {
    tripService.delete(id);
  }
}
