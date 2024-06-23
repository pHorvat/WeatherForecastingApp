package com.phorvat.weatherforecastingapp.models.user;

import com.phorvat.weatherforecastingapp.models.user.request.UserRequest;
import com.phorvat.weatherforecastingapp.models.user.request.UserUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
  @Autowired private UserService userService;

  @GetMapping()
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  @GetMapping("/{id}")
  public User getUserById(@PathVariable Integer id) {
    return userService.getUserById(id);
  }

  @GetMapping("/currentuserid")
  public Integer getCurrentUserId(@RequestHeader("Authorization") String token) {
    return userService.getUserIdFromToken(token);
  }

  @GetMapping("/current")
  public User getCurrentUser(@RequestHeader("Authorization") String token) {
    return userService.getUserDataFromToken(token);
  }

  @GetMapping("/currentuserroles")
  public List<String> getCurrentUserRoles(@RequestHeader("Authorization") String token) {
    return userService.getCustomerRolesFromToken(token);
  }

  @PostMapping("/updateLocation/{locationId}")
  public User updateUserLocation(@RequestHeader("Authorization") String token, @PathVariable Integer locationId) {
    userService.updateUserLocation(userService.getUserIdFromToken(token), locationId);
    return userService.getUserDataFromToken(token);
  }

  @PutMapping("/{userId}")
  public ResponseEntity<?> updateCurrentUser(@RequestHeader("Authorization") String token, @PathVariable Integer userId, @RequestBody UserUpdateRequest userRequest) {
    User currentUser = userService.getUserDataFromToken(token);

    if (currentUser.getRoles().contains(Role.ROLE_ADMIN)) {
      User updatedUser = userService.updateCurrentUser(userId, userRequest);
      return ResponseEntity.ok(updatedUser);
    } else {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to update this user");
    }
  }

  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable Integer id) {
    userService.deleteUser(id);
  }

}
