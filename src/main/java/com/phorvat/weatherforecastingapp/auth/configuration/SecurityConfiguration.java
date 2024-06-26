package com.phorvat.weatherforecastingapp.auth.configuration;

import com.phorvat.weatherforecastingapp.auth.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtFilter jwtFilter;
    public static final List<String> UNAUTHENTICATED_ENDPOINTS =
            List.of("/auth/register", "/auth/login");
    public static final List<String> ADMIN_ENDPOINTS =
            List.of("/locations/update", "/locations/delete", "/locations/create");

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(
                        auth -> {
                            UNAUTHENTICATED_ENDPOINTS.forEach(
                                    endpoint -> auth.requestMatchers(endpoint).permitAll());

                            ADMIN_ENDPOINTS.forEach(endpoint -> auth.requestMatchers(endpoint).hasRole("ADMIN"));

                            auth.anyRequest().authenticated();
                        })
                .csrf(csrf -> csrf.disable())
                .cors(
                        httpSecurityCorsConfigurer ->
                                httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
                .formLogin(
                        login ->
                                login
                                        .defaultSuccessUrl("/", true))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(
                        httpSecurityExceptionHandlingConfigurer ->
                                httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(
                                        (request, response, authException) ->
                                                response.setStatus(HttpStatus.UNAUTHORIZED.value())))
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(
                Arrays.asList(
                        HttpMethod.GET.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.DELETE.name()));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
