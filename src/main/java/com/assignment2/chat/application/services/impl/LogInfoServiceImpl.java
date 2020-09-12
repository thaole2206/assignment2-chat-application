package com.assignment2.chat.application.services.impl;

import com.assignment2.chat.application.entities.LogInfoEntity;
import com.assignment2.chat.application.entities.UserEntity;
import com.assignment2.chat.application.models.LogInfo;
import com.assignment2.chat.application.repositories.LogInfoRepository;
import com.assignment2.chat.application.repositories.UserRepository;
import com.assignment2.chat.application.services.LogInfoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class LogInfoServiceImpl implements LogInfoService {

    private final LogInfoRepository logInfoRepository;
    private final UserRepository userRepository;

    @Override
    public LogInfoEntity saveOrUpdate(LogInfo request) {
        UserEntity userEntity = userRepository.findUserEntityByUsername(request.getUser().getUsername()).orElse(null);

        //TODO: write to log
        return logInfoRepository.save(
                LogInfoEntity.builder()
                        .id(request.getId()).lastLoginDate(request.getLastLoginDate()).lastLogoutDate(request.getLastLogoutDate()).user(userEntity)
                        .build());
    }

    @Override
    public LogInfo findLogEntityByUser(User user) {
        String username = user.getUsername();

        if(logInfoRepository.findLogInfoEntityByUser_Username(username).isPresent()){
            LogInfoEntity entity =  logInfoRepository.findLogInfoEntityByUser_Username(username).get();
            return  LogInfo.builder()
                    .lastLoginDate(entity.getLastLoginDate()).lastLogoutDate(entity.getLastLogoutDate())
                    .id(entity.getId()).user(user)
                    .build();
        }
        return null;
    }

    @Override
    public void recordLogIn(User user, LocalDateTime date) {
        LogInfo login = findLogEntityByUser(user);

        if(login == null){
            login = new LogInfo();
        }

        login.setUser(user);
        login.setLastLoginDate(date);

        saveOrUpdate(login);
    }

    @Override
    public void recordLogOut(User user, LocalDateTime date) {
        LogInfo logout = findLogEntityByUser(user);
        logout.setLastLogoutDate(date);
        logout.setUser(user);

        saveOrUpdate(logout);
    }
}
