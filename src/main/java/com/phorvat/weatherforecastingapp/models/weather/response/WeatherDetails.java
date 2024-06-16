package hr.algebra.travelplanner.feature.destination.response;

import hr.algebra.travelplanner.feature.location.response.LocationDetails;
import lombok.Data;

import java.util.List;

@Data
public class DestinationDetails {
    private Integer id;
    private Integer countryId;
    private String countryName;
    private List<LocationDetails> locations;
}
