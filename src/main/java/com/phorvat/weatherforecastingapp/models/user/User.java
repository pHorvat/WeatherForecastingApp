package com.phorvat.weatherforecastingapp.models.user;

import com.phorvat.weatherforecastingapp.models.location.Location;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;

  private String lastName;

  private String username;

  private String email;

  private String password;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "location_id")
  private Location location_id;

  @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
  @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
  @Column(name = "role", nullable = false)
  @Enumerated(EnumType.STRING)
  private Set<Role> roles = new HashSet<>();
}
