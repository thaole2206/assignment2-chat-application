package com.assignment2.chat.application.models;

import com.assignment2.chat.application.enums.MessageType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Setter
@Getter
@Builder
public class ChatMessage {

    private MessageType type;
    private String content;
    private String sender;
    private LocalDateTime sentDate;

}
