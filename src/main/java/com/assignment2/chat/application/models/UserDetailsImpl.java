package com.assignment2.chat.application.models;

import com.assignment2.chat.application.entities.RoleEntity;
import com.assignment2.chat.application.entities.UserEntity;
import com.assignment2.chat.application.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@AllArgsConstructor
@Data
public class UserDetailsImpl implements UserDetails {

    UserEntity user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<RoleEntity> roles = user.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (RoleEntity role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return authorities;
    }


    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
}
