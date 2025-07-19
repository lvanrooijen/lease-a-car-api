package com.lvr.lease_a_car.security;

import static org.springframework.security.config.Customizer.withDefaults;

import com.lvr.lease_a_car.entities.user.UserService;
import com.lvr.lease_a_car.security.jwt.JwtAuthFilter;
import com.lvr.lease_a_car.security.jwt.UnAuthorizedEntryPoint;
import com.lvr.lease_a_car.utils.constants.routes.SecurityRoutes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class CustomSecurityFilterChain {
  private final JwtAuthFilter jwtAuthFilter;

  public CustomSecurityFilterChain(UserService userService, JwtAuthFilter jwtAuthFilter) {
    this.jwtAuthFilter = jwtAuthFilter;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers(SecurityRoutes.getNonAuthenticatedEndpoints())
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .httpBasic(withDefaults());

    http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    http.exceptionHandling(
        configures -> configures.authenticationEntryPoint(new UnAuthorizedEntryPoint()));

    return http.build();
  }
}
