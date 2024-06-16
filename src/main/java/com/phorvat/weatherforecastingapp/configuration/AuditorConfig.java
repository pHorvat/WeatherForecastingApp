package com.phorvat.weatherforecastingapp.configuration;

import com.phorvat.weatherforecastingapp.models.user.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorConfig implements AuditorAware<User> {

  @Override
  public Optional<User> getCurrentAuditor() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth == null || !auth.isAuthenticated()) {
      return Optional.empty();
    }

    User customer = (User) auth.getPrincipal();
    return Optional.of(customer);
  }
}
