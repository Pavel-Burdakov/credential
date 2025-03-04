package com.iprody.lms.credentialservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
       .csrf(csrf -> csrf
       .ignoringRequestMatchers("/auth/*") // Отключаем CSRF для этих эндпоинтов
    )
       .authorizeHttpRequests(auth -> auth
          .requestMatchers("/auth/*").permitAll()
       );
    return http.build();
  }
}