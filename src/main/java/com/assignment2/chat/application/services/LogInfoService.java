package com.assignment2.chat.application.services;

import com.assignment2.chat.application.entities.LogInfoEntity;
import com.assignment2.chat.application.models.LogInfo;
import org.springframework.security.core.userdetails.User;


public interface LogInfoService {

    LogInfoEntity saveOrUpdate(LogInfo request);
    LogInfo findLogEntityByUser(User user);
    LogInfoEntity recordLogIn(User user);
    LogInfoEntity recordLogOut(User user);
}
