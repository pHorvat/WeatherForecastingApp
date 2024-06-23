package com.phorvat.weatherforecastingapp.models.forecast;

import com.phorvat.weatherforecastingapp.models.forecast.Forecast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ForecastRepository extends JpaRepository<Forecast, Integer> {

    List<Forecast> findByLocationId(Integer locationId);

    @Query(value = "SELECT * FROM forecasts f WHERE f.location_id = :locationId ORDER BY f.forecast_timestamp DESC LIMIT 1", nativeQuery = true)
    Optional<Forecast> findFirstByLocationIdOrderByForecastTimestampDesc(Integer locationId);

    @Query(value = "SELECT * FROM forecasts f WHERE f.location_id = :locationId AND f.forecast_date BETWEEN CURDATE() AND DATE_ADD(CURDATE(), INTERVAL :days DAY) AND f.forecast_timestamp IN (SELECT MAX(f2.forecast_timestamp) FROM forecasts f2 WHERE f2.location_id = :locationId AND f2.forecast_date = f.forecast_date)", nativeQuery = true)
    List<Forecast> findLatestForecastsForNextDays(Integer locationId, Integer days);
}
