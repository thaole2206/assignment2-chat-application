package com.assignment2.chat.application.services.impl;

import com.assignment2.chat.application.entities.ChatEntity;
import com.assignment2.chat.application.entities.UserEntity;
import com.assignment2.chat.application.models.ChatMessage;
import com.assignment2.chat.application.repositories.ChatRepository;
import com.assignment2.chat.application.repositories.UserRepository;
import com.assignment2.chat.application.services.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public ChatEntity saveMessage(ChatMessage message) {
        if(userRepository.findUserEntityByUsername(message.getSender()).isPresent()) {
            UserEntity user = userRepository.findUserEntityByUsername(message.getSender()).get();
            return  ChatEntity.builder().content(message.getContent()).sender(user).sentDate(message.getSentDate()).build();
        }
        return null;
    }

    @Override
    @Transactional
    public List<ChatMessage> loadAllChatMessage() {
        List<ChatMessage> chatMessageList = new ArrayList<>();
        Pageable pageable = PageRequest.of(0, 3, Sort.by("sentDate").descending());

        Page<ChatEntity> page = chatRepository.findAll(pageable);

        for(ChatEntity chatEntity :  page){
            chatMessageList.add(
                    ChatMessage.builder().content(chatEntity.getContent()).sender(chatEntity.getSender().getUsername()).sentDate(chatEntity.getSentDate())
                            .build());
        }
        return chatMessageList;
    }
}
