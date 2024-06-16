package hr.algebra.travelplanner.feature.trip.mapper;

import hr.algebra.travelplanner.feature.destination.mapper.DestinationMapper;
import hr.algebra.travelplanner.feature.specific_location.SpecificLocation;
import hr.algebra.travelplanner.feature.specific_location.response.SpecificLocationSimple;
import hr.algebra.travelplanner.feature.trip.Trip;
import hr.algebra.travelplanner.feature.trip.request.TripRequest;
import hr.algebra.travelplanner.feature.trip.response.TripDetails;
import org.mapstruct.*;

import java.util.List;

@Mapper(
    componentModel = "spring",
    uses = {DestinationMapper.class})
public interface TripMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "customer", ignore = true)
  @Mapping(target = "destinations", source = "destinationRequests")
  Trip toEntity(TripRequest tripRequest);

  TripDetails toDetails(Trip trip);

  List<TripDetails> mapToTripDetailsList(List<Trip> trips);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @InheritConfiguration(name = "toEntity")
  void update(TripRequest tripRequest, @MappingTarget Trip target);
}
