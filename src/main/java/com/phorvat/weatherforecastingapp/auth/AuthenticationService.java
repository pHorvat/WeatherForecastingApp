package com.phorvat.weatherforecastingapp.auth;


import com.phorvat.weatherforecastingapp.auth.jwt.JwtService;
import com.phorvat.weatherforecastingapp.models.user.Role;
import com.phorvat.weatherforecastingapp.models.user.User;
import com.phorvat.weatherforecastingapp.models.user.UserRepository;
import com.phorvat.weatherforecastingapp.models.user.UserService;
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
  private final UserService userService;

  public LoginResponse login(LoginRequest request) {
    User user =
            userRepository
            .findByUsername(request.getUsername())
            .orElseThrow(
                () ->
                    new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "User with the given username does not exist"));

    if (!bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid password.");
    }

    Boolean isUserAdmin = user.getRoles().contains(Role.ROLE_ADMIN);

    return new LoginResponse(jwtService.createJwt(user), isUserAdmin);
  }

  public void register(RegisterRequest request, String token) {
    try {
      Boolean isUserAdmin = false;
      if (token != null && !token.isEmpty()){
        isUserAdmin = userService.getUserDataFromToken(token).getRoles().contains(Role.ROLE_ADMIN);
      }
      User newUser = new User();
      newUser.setName(request.getName());
      newUser.setLastName(request.getSurname());
      newUser.setUsername(request.getUsername());
      newUser.setEmail(request.getEmail());
      newUser.setPassword(new BCryptPasswordEncoder().encode(request.getPassword()));
      newUser.getRoles().add(Role.ROLE_USER);
      if (request.getIsAdmin() && isUserAdmin) {
        newUser.getRoles().add(Role.ROLE_ADMIN);
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
