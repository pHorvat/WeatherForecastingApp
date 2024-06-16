package hr.algebra.travelplanner.configuration;

import hr.algebra.travelplanner.feature.customer.Customer;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorConfig implements AuditorAware<Customer> {

  @Override
  public Optional<Customer> getCurrentAuditor() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    if (auth == null || !auth.isAuthenticated()) {
      return Optional.empty();
    }

    Customer customer = (Customer) auth.getPrincipal();
    return Optional.of(customer);
  }
}
