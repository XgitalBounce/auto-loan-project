package com.verystrong.car_loan_project.dto.Security;

import com.verystrong.car_loan_project.domain.Account_type.AccountRole;
import com.verystrong.car_loan_project.dto.AccountDto;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public record BoardPrincipal (
        String username,
        String password,
        Collection<? extends GrantedAuthority> authorities,
        String email,
        AccountRole role
        )implements UserDetails {

    public static BoardPrincipal of(String username, String password, String email, AccountRole role) {
        Set<RoleType> roleTypes = Set.of(RoleType.USER);

        return new BoardPrincipal(username, password,
                roleTypes.stream()
                .map(RoleType::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toUnmodifiableSet()),
                email,
                role);
    }

    public static BoardPrincipal from(AccountDto dto) {
        return BoardPrincipal.of(
                dto.accountId(),
                dto.password(),
                dto.email(),
                dto.role()
        );
    }

    public AccountDto toDto() {
        return AccountDto.of(
                username,
                password,
                email,
                role
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
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public enum RoleType {
        USER("ROLE_USER");

        @Getter
        private final String name;

        RoleType(String name) {
            this.name = name;
        }
    }
}
