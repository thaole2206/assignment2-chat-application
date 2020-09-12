package com.assignment2.chat.application.services;

import com.assignment2.chat.application.entities.ChatEntity;
import com.assignment2.chat.application.models.ChatMessage;

import java.util.List;


public interface ChatService  {

    ChatEntity saveMessage(ChatMessage message);
    List<ChatMessage> loadAllChatMessage();

}
