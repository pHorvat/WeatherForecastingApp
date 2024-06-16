package hr.algebra.travelplanner.feature.trip;

import hr.algebra.travelplanner.feature.customer.Customer;
import hr.algebra.travelplanner.feature.trip.mapper.TripMapper;
import hr.algebra.travelplanner.feature.trip.request.TripRequest;
import hr.algebra.travelplanner.feature.trip.response.TripDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripService {
  private final TripRepository tripRepository;
  private final TripMapper tripMapper;

  public List<TripDetails> getAllTrips() {
    return tripMapper.mapToTripDetailsList(tripRepository.findAll());
  }

  public List<TripDetails> getAllUserTrips(Integer customerId) {
    return tripMapper.mapToTripDetailsList(tripRepository.findAllByCustomerId(customerId));
  }

  public TripDetails create(Customer customer, TripRequest tripRequest) {
    Trip trip = tripMapper.toEntity(tripRequest);
    mapLocationsAndDestinationsToTrip(trip);
    trip.setCustomer(customer);
    return tripMapper.toDetails(tripRepository.save(trip));
  }

  public TripDetails update(Integer id, TripRequest tripRequest) {
    Trip trip =
        tripRepository
            .findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trip not found"));
    tripMapper.update(tripRequest, trip);
    mapLocationsAndDestinationsToTrip(trip);
    return tripMapper.toDetails(tripRepository.save(trip));
  }

  private void mapLocationsAndDestinationsToTrip(Trip trip) {
    trip.getDestinations()
        .forEach(
            destination -> {
              destination.setTrip(trip);
              destination.getLocations().forEach(location -> location.setDestination(destination));
            });
  }

  public void delete(Integer id) {
    tripRepository.deleteById(id);
  }
}
