package com.phorvat.weatherforecastingapp.models.location;

import com.phorvat.weatherforecastingapp.models.location.mapper.LocationMapper;
import com.phorvat.weatherforecastingapp.models.location.request.LocationRequest;
import com.phorvat.weatherforecastingapp.models.location.response.LocationDetails;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {
  private final LocationRepository locationRepository;
  private final LocationMapper locationMapper;

  public List<LocationDetails> getAllLocations() {
    return locationMapper.mapToLocationDetailsList(locationRepository.findAll());
  }

  public LocationDetails create(LocationRequest locationRequest) {
    Location location = locationMapper.toEntity(locationRequest);
    return locationMapper.toDetails(locationRepository.save(location));
  }

  public LocationDetails update(Integer id, LocationRequest locationRequest) {
    Location location =
        locationRepository
            .findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found"));
    locationMapper.update(locationRequest, location);
    return locationMapper.toDetails(locationRepository.save(location));
  }

  @Transactional
  public Location getLocationById(Integer id) {
    return locationRepository.findById(id).orElseThrow(() -> new RuntimeException("Location not found with id " + id));
  }

  public void delete(Integer id) {
    locationRepository.deleteById(id);
  }
}
