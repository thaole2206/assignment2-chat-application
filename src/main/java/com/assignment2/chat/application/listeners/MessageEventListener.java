package com.assignment2.chat.application.listeners;

import com.assignment2.chat.application.enums.MessageType;
import com.assignment2.chat.application.models.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class MessageEventListener {

    private static final Logger logger = LoggerFactory.getLogger(MessageEventListener.class);

    private final SimpMessageSendingOperations messagingTemplate;

    public MessageEventListener(SimpMessageSendingOperations simpMessageSendingOperations){
        this.messagingTemplate = simpMessageSendingOperations;
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info(event + ": Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        if(SecurityContextHolder.getContext().getAuthentication() != null){

            User logUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = logUser.getUsername();
            if(username != null) {
                logger.info(event + ": User Disconnected : " + username);

                messagingTemplate.convertAndSend("/topic/public",  ChatMessage.builder().type(MessageType.LEAVE).sender(username));
            }
        }
    }
}
