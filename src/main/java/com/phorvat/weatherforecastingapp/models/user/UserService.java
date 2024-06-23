package com.phorvat.weatherforecastingapp.models.user;

import com.phorvat.weatherforecastingapp.auth.jwt.JwtService;
import com.phorvat.weatherforecastingapp.models.location.Location;
import com.phorvat.weatherforecastingapp.models.location.LocationRepository;
import com.phorvat.weatherforecastingapp.models.user.request.UserRequest;
import com.phorvat.weatherforecastingapp.models.user.request.UserUpdateRequest;
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
  private final LocationRepository locationRepository;

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

  public void updateUserLocation(Integer userId, Integer locationId) {
    User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    Location location = locationRepository.findById(locationId)
            .orElseThrow(() -> new RuntimeException("Location not found"));
    user.setLocation_id(location);
    userRepository.save(user);
  }

  public User updateCurrentUser(Integer userId, UserUpdateRequest userRequest) {
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));

    user.setName(userRequest.getName());
    user.setLastName(userRequest.getSurname());
    user.setUsername(userRequest.getUsername());
    user.setEmail(userRequest.getEmail());

    return userRepository.save(user);
  }

  public void deleteUser(Integer id) {
    userRepository.deleteById(id);
  }
}