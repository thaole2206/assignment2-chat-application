package com.assignment2.chat.application.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Setter @Getter
@Table(name = "ScreenShot", schema = "Assignment2")
@NoArgsConstructor
public class ScreenShotEntity extends BaseEntity{

    @Builder
    public ScreenShotEntity(Long id, byte[] screenshot, LocalDateTime createdAt, UserEntity user) {
        super(id);
        this.screenshot = screenshot;
        this.createdAt = createdAt;
        this.user = user;
    }

    @Lob
    private byte[] screenshot;

    private LocalDateTime createdAt;

    @ManyToOne
    private UserEntity user;


}
