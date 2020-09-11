package com.assignment2.chat.application.services;

import com.assignment2.chat.application.models.LogInfo;
import org.springframework.security.core.userdetails.User;

public interface LogInfoService {

    void saveOrUpdate(LogInfo request);
    LogInfo findLogEntityByUser(User user);
}
