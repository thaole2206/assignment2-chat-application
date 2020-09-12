package com.assignment2.chat.application.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Setter@Getter
@Builder
@AllArgsConstructor
@Table(name="ChatMessage", schema = "Assignment2")
public class ChatEntity  extends BaseEntity{

    private String content;

    private LocalDateTime sentDate;

    @ManyToOne
    private UserEntity sender;


}
