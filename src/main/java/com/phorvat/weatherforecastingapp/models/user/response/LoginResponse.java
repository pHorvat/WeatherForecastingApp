package com.phorvat.weatherforecastingapp.models.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class LoginResponse {

  private final String jwtToken;

  private final Boolean isAdmin;

  @Override
  public String toString() {
    return "LoginResponse{" + "jwtToken='" + jwtToken + '\'' + ", isAdmin=" + isAdmin + '}';
  }
}
