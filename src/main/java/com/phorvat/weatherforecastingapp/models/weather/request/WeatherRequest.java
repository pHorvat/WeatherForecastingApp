package hr.algebra.travelplanner.feature.destination.request;

import hr.algebra.travelplanner.feature.location.request.LocationRequest;
import lombok.Data;

import java.util.List;

@Data
public class DestinationRequest {
  private Integer countryId;
  private List<LocationRequest> locationRequests;
}
