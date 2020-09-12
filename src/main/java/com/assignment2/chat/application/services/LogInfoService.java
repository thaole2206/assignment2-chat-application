package com.assignment2.chat.application.services;

import com.assignment2.chat.application.entities.LogInfoEntity;
import com.assignment2.chat.application.models.LogInfo;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;

public interface LogInfoService {

    LogInfoEntity saveOrUpdate(LogInfo request);
    LogInfo findLogEntityByUser(User user);
    void recordLogIn(User user, LocalDateTime date);
    void recordLogOut(User user, LocalDateTime date);
}
