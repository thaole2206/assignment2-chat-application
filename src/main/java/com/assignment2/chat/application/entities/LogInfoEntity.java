package com.assignment2.chat.application.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Setter@Getter
@Entity
@NoArgsConstructor
@Table(name="LogInfo", schema = "Assignment2")
public class LogInfoEntity extends BaseEntity{

    @Builder
    public LogInfoEntity(Long id, UserEntity user, LocalDateTime lastLoginDate, LocalDateTime lastLogoutDate) {
        super(id);
        this.user = user;
        this.lastLoginDate = lastLoginDate;
        this.lastLogoutDate = lastLogoutDate;
    }

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    private LocalDateTime lastLoginDate;
    private LocalDateTime lastLogoutDate;

}
