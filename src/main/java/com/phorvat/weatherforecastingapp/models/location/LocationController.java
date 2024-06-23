package com.phorvat.weatherforecastingapp.models.location;

import com.phorvat.weatherforecastingapp.configuration.AuditorConfig;
import com.phorvat.weatherforecastingapp.models.location.request.LocationRequest;
import com.phorvat.weatherforecastingapp.models.location.response.LocationDetails;
import com.phorvat.weatherforecastingapp.models.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationController {
  @Autowired private LocationService locationService;
  @Autowired private AuditorConfig auditorConfig;

  @GetMapping()
  public List<LocationDetails> getAllLocations() {
    return locationService.getAllLocations();
  }


  @PostMapping()
  public LocationDetails create(@RequestBody LocationRequest locationRequest) {
    User user =
        auditorConfig
            .getCurrentAuditor()
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized"));
    return locationService.create(locationRequest);
  }

  @PutMapping("/{id}")
  public LocationDetails update(@PathVariable Integer id, @RequestBody LocationRequest locationRequest) {
    return locationService.update(id, locationRequest);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Integer id) {
    locationService.delete(id);
  }
}
