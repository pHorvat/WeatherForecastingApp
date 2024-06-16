package hr.algebra.travelplanner.feature.destination.mapper;

import hr.algebra.travelplanner.feature.destination.Destination;
import hr.algebra.travelplanner.feature.destination.request.DestinationRequest;
import hr.algebra.travelplanner.feature.destination.response.DestinationDetails;
import hr.algebra.travelplanner.feature.location.mapper.LocationMapper;
import org.mapstruct.*;

@Mapper(
    componentModel = "spring",
    uses = {LocationMapper.class, DestinationReferenceMapper.class})
public interface DestinationMapper {
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "trip", ignore = true)
  @Mapping(target = "locations", source = "locationRequests")
  @Mapping(target = "country", source = "countryId")
  Destination toEntity(DestinationRequest destinationRequest);

  @Mapping(target = "countryId", source = "country.id")
  @Mapping(target = "countryName", source = "country.name")
  DestinationDetails toDetails(Destination destination);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @InheritConfiguration(name = "toEntity")
  void update(DestinationRequest destinationRequest, @MappingTarget Destination target);
}
