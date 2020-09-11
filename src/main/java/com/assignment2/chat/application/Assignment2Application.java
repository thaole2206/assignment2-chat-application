package com.assignment2.chat.application;

import com.assignment2.chat.application.entities.UserEntity;
import com.assignment2.chat.application.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableScheduling
public class Assignment2Application{

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Assignment2Application.class);

        builder.headless(false);

        ConfigurableApplicationContext context = builder.run(args);
    }



}
