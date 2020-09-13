package com.assignment2.chat.application.services;

import com.assignment2.chat.application.entities.ChatEntity;
import com.assignment2.chat.application.entities.UserEntity;
import com.assignment2.chat.application.models.ChatMessage;
import com.assignment2.chat.application.repositories.ChatRepository;
import com.assignment2.chat.application.repositories.ScreenShotRepository;
import com.assignment2.chat.application.repositories.UserRepository;
import com.assignment2.chat.application.services.impl.ChatServiceImpl;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
@NoArgsConstructor
class ChatServiceTest {

    private ChatService service;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ChatRepository chatRepository;

    private ChatEntity chatEntity;
    private UserEntity userEntity;
    private ChatMessage chatMessage;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        service = new ChatServiceImpl(chatRepository, userRepository);

        String content = "content";
        LocalDateTime date = LocalDateTime.now();

        userEntity = UserEntity.builder().username("username").build();
        chatEntity = ChatEntity.builder().content(content).sender(userEntity).sentDate(date).build();
        chatMessage = ChatMessage.builder().content(content).sender(userEntity.getUsername()).sentDate(date).build();
    }

    @Test
    void saveMessagePositive() {
        given(userRepository.findUserEntityByUsername(any(String.class))).willReturn(Optional.of(userEntity));
        given(chatRepository.save(any(ChatEntity.class))).willReturn(chatEntity);
        ChatEntity savedEntity = service.saveMessage(chatMessage);
        assertEquals(chatEntity, savedEntity);
    }

    @Test
    void saveMessageNegative() {
        ChatEntity savedEntity = service.saveMessage(chatMessage);
        assertNull(savedEntity);
    }

    @Test
    void loadAllChatMessagePositive() {
        List<ChatEntity> chatEntities = new ArrayList<>(Arrays.asList(chatEntity));
        Page<ChatEntity> page = new PageImpl(chatEntities);
        given(chatRepository.findAll()).willReturn(chatEntities);

        List<ChatMessage> chatMessageList = service.loadAllChatMessage();

        assertEquals(page.getTotalElements(), chatMessageList.size());
    }

    @Test
    void loadAllChatMessageNegative() {
        List<ChatMessage> chatMessageList = service.loadAllChatMessage();

        assertEquals(0, chatMessageList.size());
    }
}