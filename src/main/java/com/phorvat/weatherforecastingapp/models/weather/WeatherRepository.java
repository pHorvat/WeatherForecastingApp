package com.phorvat.weatherforecastingapp.models.weather;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;

public interface WeatherRepository extends JpaRepository<Weather, Integer> {

    List<Weather> findByLocationId(Integer location_id);

    @Query(value = "SELECT * FROM weathers w WHERE w.location_id = :locationId ORDER BY w.timestamp DESC LIMIT 1", nativeQuery = true)
    Optional<Weather> findFirstByLocationIdOrderByTimestampDesc(Integer locationId);
}
