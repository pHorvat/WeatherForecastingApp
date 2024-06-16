package com.phorvat.weatherforecastingapp.models.location.mapper;

import com.phorvat.weatherforecastingapp.models.location.Location;
import com.phorvat.weatherforecastingapp.models.location.request.LocationRequest;
import com.phorvat.weatherforecastingapp.models.location.response.LocationDetails;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LocationMapper {

  @Mapping(target = "id", ignore = true)
  Location toEntity(LocationRequest locationDetails);



  LocationDetails toDetails(Location location);

  List<LocationDetails> mapToLocationDetailsList(List<Location> locations);

  void update(LocationRequest locationRequest, @MappingTarget Location location);
}
