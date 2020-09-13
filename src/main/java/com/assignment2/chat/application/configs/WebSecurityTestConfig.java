package com.assignment2.chat.application.configs;

import com.assignment2.chat.application.entities.RoleEntity;
import com.assignment2.chat.application.entities.UserEntity;
import com.assignment2.chat.application.models.UserDetailsImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.assignment2.chat.application.constants.CommonConstants.ROLE_ADMIN;
import static com.assignment2.chat.application.constants.CommonConstants.ROLE_MEMBER;

@TestConfiguration
public class WebSecurityTestConfig {
    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        GrantedAuthority authority = new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return ROLE_ADMIN;
            }
        };

        User user = new User("username", "password", new ArrayList<>(Arrays.asList(authority))) ;
        Set<RoleEntity> roles = new HashSet<>(
                        Arrays.asList(
                                RoleEntity.builder().name(ROLE_MEMBER).build(),
                                RoleEntity.builder().name(ROLE_ADMIN).build())
                        );

        UserDetails member = new UserDetailsImpl(
                UserEntity.builder()
                        .username("member@gmail.com")
                        .password("123456")
                        .roles(new HashSet<>(Arrays.asList(RoleEntity.builder().name(ROLE_MEMBER).build())))
                        .build());

        UserDetails admin = new UserDetailsImpl(UserEntity.builder().username("admin@gmail.com").password("123456").roles(roles).build());

        return new InMemoryUserDetailsManager(Arrays.asList(
                member, admin
        ));
    }
}
