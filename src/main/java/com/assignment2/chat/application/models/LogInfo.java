package com.assignment2.chat.application.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

import java.util.Date;

@Setter@Getter
public class LogInfo {

    private Long id;
    private Date lastLoginDate;
    private Date lastLogoutDate;
    private User user;
}
