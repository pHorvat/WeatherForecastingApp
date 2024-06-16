package hr.algebra.travelplanner.feature.destination;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DestinationService {

  private final DestinationRepository destinationRepository;

  public List<Destination> getAllDestinations() {
    return destinationRepository.findAll();
  }

}
