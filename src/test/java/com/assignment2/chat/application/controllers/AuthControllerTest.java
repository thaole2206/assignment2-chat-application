package com.assignment2.chat.application.controllers;

import com.assignment2.chat.application.configs.WebSecurityTestConfig;
import com.assignment2.chat.application.services.ChatService;
import com.assignment2.chat.application.services.LogInfoService;
import com.assignment2.chat.application.services.impl.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static com.assignment2.chat.application.constants.CommonConstants.ROLE_ADMIN;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthController.class)
@ContextConfiguration(
        classes = WebSecurityTestConfig.class
)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private LogInfoService logInfoService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private ChatService chatService;

    @Test
    void showLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    @WithUserDetails("member@gmail.com")
    void logout() throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        mockMvc.perform(get("/signout"))
                .andExpect(status().is3xxRedirection());

        verify(logInfoService, times(1)).recordLogOut(user);
        verifyNoMoreInteractions(logInfoService);
    }

    @WithUserDetails("member@gmail.com")
    @Test
    void recordLogInPositive() throws Exception {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        mockMvc.perform(get("/home"))
                .andExpect(status().is3xxRedirection());

        verify(logInfoService, times(1)).recordLogIn(user);
        verifyNoMoreInteractions(logInfoService);
    }

    @Test
    void recordLogInNegative() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void accessDenied() throws Exception {
        mockMvc.perform(get("/403"))
                .andExpect(status().isOk())
                .andExpect(view().name("/error/403"));
    }

}