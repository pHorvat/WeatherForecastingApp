package com.phorvat.weatherforecastingapp.models;

import com.phorvat.weatherforecastingapp.auth.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final JwtService jwtService;

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public User getUserById(Integer id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
  }

  public Integer getUserIdFromToken(String token) {
    return jwtService.getUserIdFromJwt(token);
  }

  public User getUserDataFromToken(String token) {
    Integer customerId = jwtService.getUserIdFromJwt(token);
    return userRepository
        .findById(customerId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
  }

  public List<String> getCustomerRolesFromToken(String token) {
    return jwtService.getUserRolesFromJwt(token);
  }
}
