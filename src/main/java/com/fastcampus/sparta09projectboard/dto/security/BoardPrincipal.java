package com.fastcampus.sparta09projectboard.dto.security;

import com.fastcampus.sparta09projectboard.dto.UserAccountDto;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record BoardPrincipal(
    String username,
    String password,
    Collection<? extends GrantedAuthority> authorities,
    String email)
    implements UserDetails {

  public static BoardPrincipal of(String username, String password, String email) {
    Set<RoleType> roleTypes = Set.of(RoleType.USER);
    return new BoardPrincipal(
        username,
        password,
        roleTypes.stream()
            .map(RoleType::getName)
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toUnmodifiableSet()),
        email);
  }

    public static BoardPrincipal from(UserAccountDto dto) {
        return BoardPrincipal.of(
                dto.userId(),
                dto.userPassword(),
                dto.email()
        );
    }

    public UserAccountDto toDto() {
        return UserAccountDto.of(
                username,
                password,
                email
        );
    }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  // 인증 - 로그인 유무, 권한 - 기능에 대한 접근 여부
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public enum RoleType {
    USER("ROLE_USER");

    @Getter private final String name;

    RoleType(String name) {
      this.name = name;
    }
  }
}
