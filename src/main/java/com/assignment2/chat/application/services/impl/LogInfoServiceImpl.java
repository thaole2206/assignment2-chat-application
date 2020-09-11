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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class LogInfoServiceImpl implements LogInfoService {

    private final LogInfoRepository logInfoRepository;
    private final UserRepository userRepository;

    @Override
    public void saveOrUpdate(LogInfo request) {
        UserEntity userEntity = userRepository.findUserEntityByUsername(request.getUser().getUsername()).orElse(null);
        LogInfoEntity entity = new LogInfoEntity();

        entity.setId(request.getId());
        entity.setLastLoginDate(request.getLastLoginDate());
        entity.setLastLogoutDate(request.getLastLogoutDate());
        entity.setUser(userEntity);

        LogInfoEntity savedEntity = logInfoRepository.save(entity);
        log.info("Saved login/ logout info: " + savedEntity.toString());
    }

    @Override
    public LogInfo findLogEntityByUser(User user) {
        UserEntity userEntity = userRepository.findUserEntityByUsername(user.getUsername()).orElse(null);
        if(userEntity == null){
            throw new UsernameNotFoundException(user.getUsername());
        }

        LogInfoEntity entity =  logInfoRepository.findLogInfoEntityByUser(userEntity).orElse(null);

        LogInfo log = new LogInfo();
        log.setLastLoginDate(entity.getLastLoginDate() != null ? entity.getLastLoginDate() : null);
        log.setLastLogoutDate(entity.getLastLogoutDate() != null ? entity.getLastLogoutDate() : null);
        log.setId(entity.getId());

        return log;
    }
}
