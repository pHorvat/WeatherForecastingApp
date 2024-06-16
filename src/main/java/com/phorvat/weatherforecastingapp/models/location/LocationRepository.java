package hr.algebra.travelplanner.feature.trip;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Integer> {
    List<Trip> findAllByCustomerId(Integer customerId);
}
