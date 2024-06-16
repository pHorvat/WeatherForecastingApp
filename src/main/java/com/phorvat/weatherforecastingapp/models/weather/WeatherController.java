package hr.algebra.travelplanner.feature.destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/destinations")
public class DestinationController {
  @Autowired private DestinationService destinationService;

  @GetMapping
  public List<Destination> getAllDestinations() {
    return destinationService.getAllDestinations();
  }
}
