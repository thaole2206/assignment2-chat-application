package com.assignment2.chat.application.seeders;

import com.assignment2.chat.application.entities.RoleEntity;
import com.assignment2.chat.application.entities.UserEntity;
import com.assignment2.chat.application.repositories.RoleRepository;
import com.assignment2.chat.application.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;

import static com.assignment2.chat.application.constants.CommonConstants.ROLE_ADMIN;
import static com.assignment2.chat.application.constants.CommonConstants.ROLE_MEMBER;

//@AllArgsConstructor
//@Component
public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {
        RoleEntity roleAdmin = new RoleEntity();
        RoleEntity roleMember = new RoleEntity();
        // Roles
        if (roleRepository.findRoleEntityByName(ROLE_ADMIN) == null) {
            roleAdmin.setName(ROLE_ADMIN);
        }

        if (roleRepository.findRoleEntityByName(ROLE_MEMBER) == null) {
            roleMember.setName(ROLE_MEMBER);
        }

        String adminUsername = "admin@gmail.com";
        String password = "123456";

        // Admin account
        if (userRepository.findUserEntityByUsername(adminUsername) == null) {
            UserEntity admin = new UserEntity();
            admin.setUsername(adminUsername);
            admin.setPassword(passwordEncoder.encode(password));
            HashSet<RoleEntity> roles = new HashSet<>();
            roles.add(roleAdmin);
            roles.add(roleMember);
            admin.setRoles(roles);
            userRepository.save(admin);
        }

        String memberUsername = "member@gmail.com";
        // Member account
        if (userRepository.findUserEntityByUsername(memberUsername) == null) {
            UserEntity user = new UserEntity();
            user.setUsername(memberUsername);
            user.setPassword(passwordEncoder.encode(password));
            HashSet<RoleEntity> roles = new HashSet<>();
            roles.add(roleMember);
            user.setRoles(roles);
            userRepository.save(user);
        }
    }
}
