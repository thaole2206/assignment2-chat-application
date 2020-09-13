package com.assignment2.chat.application.controllers;

import com.assignment2.chat.application.configs.WebSecurityTestConfig;
import com.assignment2.chat.application.models.ChatMessage;
import com.assignment2.chat.application.services.ChatService;
import com.assignment2.chat.application.services.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(MemberController.class)
@ContextConfiguration(
        classes = WebSecurityTestConfig.class
)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ChatService service;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;


    @Test
    @WithUserDetails("member@gmail.com")
    void getHomePagePositive() throws Exception {
        LocalDateTime date = LocalDateTime.now();
        ChatMessage chatMessage = ChatMessage.builder().content("abc").sender("member").sentDate(date).build();
        List<ChatMessage> chatMessageList = new ArrayList<>(Arrays.asList(chatMessage, chatMessage));
        given(service.loadAllChatMessage()).willReturn(chatMessageList);

        mockMvc.perform(get("/user/home/"))
                .andExpect(status().isOk())
                .andExpect(view().name("/homepage"))
                .andExpect(model().attribute("allMessages", hasSize(2)))
                .andExpect(model().attribute("allMessages", hasItem(allOf(
                                    hasProperty("content", is("abc")),
                                    hasProperty("sender", is("member"))
                        ))))
                .andExpect(model().attribute("allMessages", hasItem(allOf(
                                    hasProperty("content", is("abc")),
                                    hasProperty("sender", is("member"))))));

        verify(service, times(1)).loadAllChatMessage();
        verifyNoMoreInteractions(service);
    }

    @Test
    @WithUserDetails("member@gmail.com")
    void getHomePageWithNoResult() throws Exception {
        given(service.loadAllChatMessage()).willReturn(new ArrayList<>());
        mockMvc.perform(get("/user/home/"))
                .andExpect(status().isOk())
                .andExpect(view().name("/homepage"))
                .andExpect(model().attribute("allMessages", hasSize(0)))
               ;

        verify(service, times(1)).loadAllChatMessage();
        verifyNoMoreInteractions(service);
    }

    @Test
    void getIndexPageRedirectToLogin() throws Exception {
        mockMvc.perform(get("/user/home/"))
                .andExpect(status().is3xxRedirection());

        verifyNoMoreInteractions(service);
    }
}