package com.assignment2.chat.application.services;

import com.assignment2.chat.application.constants.CommonConstants;
import com.assignment2.chat.application.entities.LogInfoEntity;
import com.assignment2.chat.application.entities.UserEntity;
import com.assignment2.chat.application.models.LogInfo;
import com.assignment2.chat.application.repositories.LogInfoRepository;
import com.assignment2.chat.application.repositories.UserRepository;
import com.assignment2.chat.application.services.impl.LogInfoServiceImpl;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.*;

import static com.assignment2.chat.application.constants.CommonConstants.ROLE_ADMIN;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@Slf4j
@RunWith(MockitoJUnitRunner.class)
@NoArgsConstructor
class LogInfoServiceTest {

    private LogInfoService service;

    @Mock
    private LogInfoRepository logInfoRepository;
    @Mock
    private UserRepository userRepository;

    private LogInfo logInfoReq;
    private LogInfoEntity logInfoEntity;
    private User user;
    private UserEntity userEntity;
    private LocalDateTime date;

    private final String  username = "admin@gmail.com";
    private final String password = "Thao@12345";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new LogInfoServiceImpl(logInfoRepository, userRepository);

        userEntity = UserEntity.builder().username(username).password(password).build();

        GrantedAuthority authority = new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return ROLE_ADMIN;
            }
        };

        user = new User(username, password, new ArrayList<>(Arrays.asList(authority))) ;

        logInfoReq = LogInfo.builder().lastLoginDate(date).lastLogoutDate(date).id(1L).user(user).build();

        logInfoEntity = LogInfoEntity.builder().lastLoginDate(date).lastLogoutDate(date).user(userEntity).build();
    }

    @Test
    void saveOrUpdatePositive() {
        given(userRepository.findUserEntityByUsername(username)).willReturn(Optional.of(userEntity));
        given(logInfoRepository.save(any(LogInfoEntity.class))).willReturn(logInfoEntity);

        LogInfoEntity savedEntity = service.saveOrUpdate(logInfoReq);

        assertEquals(userEntity.getId(), savedEntity.getUser().getId());
        assertEquals(logInfoEntity.getLastLoginDate(), savedEntity.getLastLoginDate());
        assertEquals(logInfoEntity.getLastLogoutDate(), savedEntity.getLastLogoutDate());
    }

    @Test
    void saveOrUpdateNegative(){
        given(logInfoRepository.save(any(LogInfoEntity.class))).willReturn(null);
        LogInfoEntity savedEntity = service.saveOrUpdate(logInfoReq);

        assertNull(savedEntity);
    }

    @Test
    void findLogEntityByUsernamePositive() {
        given(logInfoRepository.findLogInfoEntityByUser_Username(username)).willReturn(Optional.of(logInfoEntity));

        LogInfo logInfo = service.findLogEntityByUser(user);

        assertEquals(logInfoEntity.getUser().getUsername(), logInfo.getUser().getUsername());
        assertEquals(logInfoEntity.getLastLoginDate(), logInfo.getLastLoginDate());
        assertEquals(logInfoEntity.getLastLogoutDate(), logInfo.getLastLogoutDate());
    }

    @Test
    void findLogEntityByUserNegative() {
        given(logInfoRepository.findLogInfoEntityByUser_Username(username)).willReturn(Optional.empty());
        LogInfo logInfo = service.findLogEntityByUser(user);
        assertNull(logInfo);
    }

    @Test
    void recordLogInPositive(){
        //TODO:
    }

    @Test
    void recordLogInNegative(){
        //TODO:
    }
}