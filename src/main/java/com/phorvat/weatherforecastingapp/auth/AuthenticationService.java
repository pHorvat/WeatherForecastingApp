package com.phorvat.weatherforecastingapp.auth;


import com.phorvat.weatherforecastingapp.auth.jwt.JwtService;
import com.phorvat.weatherforecastingapp.models.location.LocationService;
import com.phorvat.weatherforecastingapp.models.user.Role;
import com.phorvat.weatherforecastingapp.models.user.User;
import com.phorvat.weatherforecastingapp.models.user.UserRepository;
import com.phorvat.weatherforecastingapp.models.user.request.LoginRequest;
import com.phorvat.weatherforecastingapp.models.user.request.RegisterRequest;
import com.phorvat.weatherforecastingapp.models.user.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository userRepository;
  private final JwtService jwtService;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final LocationService locationService;

  public LoginResponse login(LoginRequest request) {
    User user =
            userRepository
            .findByUsername(request.getUsername())
            .orElseThrow(
                () ->
                    new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Customer with the given username does not exist"));

    if (!bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid password.");
    }

    Boolean isCustomerAdmin = user.getRoles().contains(Role.ROLE_ADMIN);

    return new LoginResponse(jwtService.createJwt(user), isCustomerAdmin);
  }

  public void register(RegisterRequest request) {
    //TODO: implement customer mapper
    try {
      User newUser = new User();
      newUser.setName(request.getName());
      newUser.setLastName(request.getSurname());
      newUser.setUsername(request.getUsername());
      newUser.setEmail(request.getEmail());
      newUser.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
      newUser.getRoles().add(Role.ROLE_USER);
      if (request.getIsAdmin()) {
        newUser.getRoles().add(Role.ROLE_ADMIN);
        newUser.setLocation_id(locationService.getLocationById(1));
      }
      userRepository.save(newUser);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  public void logout(String token){
    jwtService.invalidateToken(token);
  }
}
