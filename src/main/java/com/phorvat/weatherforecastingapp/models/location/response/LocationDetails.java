package com.phorvat.weatherforecastingapp.models.location.response;

import hr.algebra.travelplanner.feature.destination.response.DestinationDetails;
import lombok.Data;

import java.util.List;

@Data
public class TripDetails {
    private Integer id;
    private String name;
    private String startDate;
    private String endDate;
    private List<DestinationDetails> destinations;
}
