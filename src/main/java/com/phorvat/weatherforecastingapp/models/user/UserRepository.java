package com.phorvat.weatherforecastingapp.models;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String email);

    @Query("SELECT c FROM User c")
    @EntityGraph(attributePaths = "trips")
    List<User> findAllCustomersWithTrips();
}
