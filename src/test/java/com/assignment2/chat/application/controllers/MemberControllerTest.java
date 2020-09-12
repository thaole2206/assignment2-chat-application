package com.assignment2.chat.application.controllers;

import com.assignment2.chat.application.entities.ChatEntity;
import com.assignment2.chat.application.entities.UserEntity;
import com.assignment2.chat.application.models.ChatMessage;
import com.assignment2.chat.application.services.ChatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChatService service;

    @Test
    void getIndexPage() {
        LocalDateTime date = LocalDateTime.now();
        ChatMessage chatMessage = ChatMessage.builder().content("abc").sender("member").sentDate(date).build();
        List<ChatMessage> chatMessageList = new ArrayList<>(Arrays.asList(chatMessage));
        given(service.loadAllChatMessage()).willReturn(chatMessageList);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/home/").andExpect());

    }
}