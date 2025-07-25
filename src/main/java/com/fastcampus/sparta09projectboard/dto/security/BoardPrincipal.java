package com.fastcampus.sparta09projectboard.dto.security;

import com.fastcampus.sparta09projectboard.dto.UserAccountDto;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public record BoardPrincipal(
        String username,
        String password,
        Collection<? extends GrantedAuthority> authorities,
        String email,
        String nickname
) implements UserDetails {

  public static BoardPrincipal of(String username, String password, String email, String nickname) {
    // 지금은 인증만 하고 권한을 다루고 있지 않아서 임의로 세팅한다.
    Set<RoleType> roleTypes = Set.of(RoleType.USER);

    return new BoardPrincipal(
            username,
            password,
            roleTypes.stream()
                    .map(RoleType::getName)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toUnmodifiableSet())
            ,
            email,
            nickname
    );
  }

  public static BoardPrincipal from(UserAccountDto dto) {
    return BoardPrincipal.of(
            dto.userId(),
            dto.userPassword(),
            dto.email(),
            dto.nickname()
    );
  }

  public UserAccountDto toDto() {
    return UserAccountDto.of(
            username,
            password,
            email,
            nickname
    );
  }


  @Override public String getUsername() { return username; }
  @Override public String getPassword() { return password; }
  @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }

  @Override public boolean isAccountNonExpired() { return true; }
  @Override public boolean isAccountNonLocked() { return true; }
  @Override public boolean isCredentialsNonExpired() { return true; }
  @Override public boolean isEnabled() { return true; }


  public enum RoleType {
    USER("ROLE_USER");

    @Getter private final String name;

    RoleType(String name) {
      this.name = name;
    }
  }

}
