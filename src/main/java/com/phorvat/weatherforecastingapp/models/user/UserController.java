package com.phorvat.weatherforecastingapp.models.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable Integer id) {
    userService.deleteUser(id);
  }

}
