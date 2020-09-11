package com.assignment2.chat.application.configs;

import com.assignment2.chat.application.services.impl.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@AllArgsConstructor
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    UserDetailsServiceImpl userDetailsServiceImpl;


    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // Get AuthenticationManager bean
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Password encoder
        return new BCryptPasswordEncoder();
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
            auth.userDetailsService(userDetailsServiceImpl) .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors() // prevent request from another domain
                .and().authorizeRequests()
                .antMatchers("/admin/**", "/home/**", "/chat/**", "/topic/**", "/app/**").authenticated()
                .antMatchers("/register").permitAll()
                .antMatchers("/home/**").authenticated()
                .and() .formLogin().loginPage("/login").usernameParameter("email").passwordParameter("password").defaultSuccessUrl("/home").failureUrl("/login?error")
                .and().exceptionHandling().accessDeniedPage("/403");
        http.csrf().disable();

    }
}
