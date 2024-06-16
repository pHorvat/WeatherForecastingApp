package com.phorvat.weatherforecastingapp.auth;

import com.phorvat.weatherforecastingapp.models.user.request.LoginRequest;
import com.phorvat.weatherforecastingapp.models.user.request.RegisterRequest;
import com.phorvat.weatherforecastingapp.models.user.response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

  @Autowired private AuthenticationService authenticationService;

  @PostMapping("/login")
  public LoginResponse login(@RequestBody LoginRequest loginRequest) {
    return authenticationService.login(loginRequest);
  }

  @PostMapping("/register")
  public void register(@RequestBody RegisterRequest registerRequest) {
    authenticationService.register(registerRequest);
  }

  @GetMapping("/logout")
  public void logout(@RequestHeader("Authorization") String bearer) {
    authenticationService.logout(bearer.substring(7));
  }
}
