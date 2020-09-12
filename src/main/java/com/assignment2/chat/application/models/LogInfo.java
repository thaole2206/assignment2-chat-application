package com.assignment2.chat.application.models;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;

@Setter@Getter
@NoArgsConstructor
public class LogInfo {

    @Builder
    public LogInfo(Long id, LocalDateTime lastLoginDate, LocalDateTime lastLogoutDate, User user) {
        this.id = id;
        this.lastLoginDate = lastLoginDate;
        this.lastLogoutDate = lastLogoutDate;
        this.user = user;
    }

    private Long id;
    private LocalDateTime lastLoginDate;
    private LocalDateTime lastLogoutDate;
    private User user;
}
