package com.assignment2.chat.application.services.impl;

import com.assignment2.chat.application.entities.RoleEntity;
import com.assignment2.chat.application.entities.UserEntity;
import com.assignment2.chat.application.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;


@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findUserEntityByUsername(username).orElse(null);
        if(user == null){
            throw new UsernameNotFoundException(username);
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        Set<RoleEntity> roles = user.getRoles();
        for (RoleEntity role : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new User(user.getUsername(),  user.getPassword(), grantedAuthorities);
    }


    public UserEntity findUserByUsername(String username){
        return userRepository.findUserEntityByUsername(username).orElse(null);
    }


}
