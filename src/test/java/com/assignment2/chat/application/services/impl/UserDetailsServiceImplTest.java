package com.assignment2.chat.application.services.impl;

import com.assignment2.chat.application.entities.UserEntity;
import com.assignment2.chat.application.repositories.UserRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
@NoArgsConstructor
class UserDetailsServiceImplTest {

    private UserDetailsServiceImpl userDetailsService;

    @Mock
   private UserRepository userRepository;

    private UserEntity userEntity;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        userDetailsService = new UserDetailsServiceImpl(userRepository);

        userEntity = new UserEntity();
        userEntity.setUsername("thaolt15@fsoft.com.vn");
        userEntity.setPassword("123456");

//        RoleEntity admin = new RoleEntity();
//        admin.setName(CommonConstants.ROLE_ADMIN);
//        RoleEntity member = new RoleEntity();
//        member.setName(CommonConstants.ROLE_MEMBER);
//
//        Set<RoleEntity> roles = new HashSet<>();
//        roles.add(admin);
//        roles.add(member);
//
//        userEntity.setRoles(roles);

    }

    @Test
    void loadUserByUsernamePostive() {
        given(userRepository.findUserEntityByUsername("thaolt15@fsoft.com.vn")).willReturn(Optional.of(userEntity));

        UserDetails userDetails = userDetailsService.loadUserByUsername("thaolt15@fsoft.com.vn");
        assertNotNull(userDetails);
        assertEquals(Optional.of(userEntity).orElse(null).getUsername(), userDetails.getUsername());
    }

    @Test
    void loadUserByUsernameNegative() {
        given(userRepository.findUserEntityByUsername("thaolt25@fsoft.com.vn")).willReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("thaolt25@fsoft.com.vn"));
    }

    @Test
    void findUserByUsernamePostive(){
        given(userRepository.findUserEntityByUsername("thaolt15@fsoft.com.vn")).willReturn(Optional.of(userEntity));

        UserEntity entity = userDetailsService.findUserByUsername("thaolt15@fsoft.com.vn");

        assertNotNull(entity);
        assertEquals(entity.getUsername(), userEntity.getUsername());
        assertEquals(entity, userEntity);
    }

    @Test
    void findUserByUsernameNegative(){
        given(userRepository.findUserEntityByUsername("thaolt15@fsoft.com.vn")).willReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("thaolt25@fsoft.com.vn"));
    }
}