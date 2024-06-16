package com.phorvat.weatherforecastingapp.models.request;

import lombok.Data;

@Data
public class LoginRequest {

  private String username;

  private String password;
}
