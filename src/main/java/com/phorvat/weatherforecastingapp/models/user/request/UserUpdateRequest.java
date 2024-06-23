package com.phorvat.weatherforecastingapp.models.user.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserUpdateRequest {
    @NotBlank private String name;
    @NotBlank private String surname;
    @NotBlank private String username;
    @NotBlank private String email;
}
