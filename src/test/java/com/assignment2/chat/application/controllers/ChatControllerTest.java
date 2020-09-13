package com.assignment2.chat.application.controllers;

import com.assignment2.chat.application.configs.WebSecurityTestConfig;
import com.assignment2.chat.application.services.ChatService;
import com.assignment2.chat.application.services.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


@RunWith(SpringRunner.class)
@WebMvcTest(ChatController.class)
@ContextConfiguration(
        classes = WebSecurityTestConfig.class
)
class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private  ChatService chatService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void sendPositive() {
    }

    @Test
    void sendNegative() {
    }

    @Test
    void addUserPositive() {
    }

    @Test
    void addUserNegative() {
    }
}