package com.phorvat.weatherforecastingapp.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
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

  @GetMapping("/currentuser")
  public User getCurrentUser(@RequestHeader("Authorization") String token) {
    return userService.getUserDataFromToken(token);
  }

  @GetMapping("/currentuserroles")
  public List<String> getCurrentUserRoles(@RequestHeader("Authorization") String token) {
    return userService.getCustomerRolesFromToken(token);
  }
}
