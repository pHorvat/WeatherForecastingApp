package com.phorvat.weatherforecastingapp.models.user.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class LoginResponse {

  private final String jwtToken;

  private final Boolean isAdmin;

}
