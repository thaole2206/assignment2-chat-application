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
import java.util.NoSuchElementException;

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
        RoleEntity roleAdmin;
        try {
            roleAdmin = roleRepository.findRoleEntityByName(ROLE_ADMIN).get();
        } catch (NoSuchElementException e){
           roleAdmin = new RoleEntity();
           roleAdmin.setName(ROLE_ADMIN);
        }

        RoleEntity roleMember;
        try {
            roleMember = roleRepository.findRoleEntityByName(ROLE_MEMBER).get();
        } catch (NoSuchElementException e){
            roleMember = new RoleEntity();
            roleMember.setName(ROLE_MEMBER);
        }

        String adminUsername = "admin@gmail.com";
        String password = "Thao@12345";
        System.out.println(passwordEncoder.encode(password));
        try {
            UserEntity  user = userRepository.findUserEntityByUsername(adminUsername).get();
            userRepository.save(user);
        } catch (NoSuchElementException e){
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
        try {
            UserEntity  user = userRepository.findUserEntityByUsername(memberUsername).get();
            userRepository.save(user);
        } catch (NoSuchElementException e){
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
