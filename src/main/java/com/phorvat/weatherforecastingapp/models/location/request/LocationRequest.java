package com.phorvat.weatherforecastingapp.models.location.request;

import hr.algebra.travelplanner.feature.destination.request.DestinationRequest;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TripRequest {
  private String name;
  private LocalDate startDate;
  private LocalDate endDate;
  private List<DestinationRequest> destinationRequests;
}
