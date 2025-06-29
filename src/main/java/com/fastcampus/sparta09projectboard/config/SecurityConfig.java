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
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .requestMatchers("/.well-known/**", "/favicon.ico", "/robots.txt");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        // API 경로 허용
                        .requestMatchers("/api/**").permitAll()
                        // 로그인/회원가입 관련 경로 모두 허용
                        .requestMatchers("/logout", "/login", "/login/**", "/signup", "/error").permitAll()
                        // 홈페이지와 게시판 목록, 게시글 상세조회, 수정폼 조회 허용
                        .requestMatchers(HttpMethod.GET, "/", "/articles", "/articles/*", "/articles/*/form").permitAll()
                        // 게시글 삭제 POST 요청 허용 (비밀번호 기반)
                        .requestMatchers(HttpMethod.POST, "/articles/*/delete").permitAll()
                        // 게시글 수정 POST 요청 허용 (비밀번호 기반)
                        .requestMatchers(HttpMethod.POST, "/articles/*/form").permitAll()
                        // 나머지는 인증 필요
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/articles", true)
                        .failureUrl("/login?error=true")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
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
