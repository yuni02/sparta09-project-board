package com.fastcampus.sparta09projectboard.config;

import com.fastcampus.sparta09projectboard.dto.UserAccountDto;
import com.fastcampus.sparta09projectboard.dto.security.BoardPrincipal;
import com.fastcampus.sparta09projectboard.repository.UserAccountRepository;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.authorizeHttpRequests(
            auth ->
                auth.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/", "/articles")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .formLogin(withDefaults())
        .logout(logout -> logout.logoutSuccessUrl("/"))
        .build();
  }

  @Bean
  public UserDetailsService userDetailsService(UserAccountRepository userAccountRepository) {
    return username ->
        userAccountRepository
            .findById(username)
            .map(UserAccountDto::from)
            .map(BoardPrincipal::from)
            .orElseThrow(() -> new UsernameNotFoundException("유저 이름을 찾을 수 없음."));
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }
}
