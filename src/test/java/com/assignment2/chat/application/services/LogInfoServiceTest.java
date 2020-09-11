package com.assignment2.chat.application.services;

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
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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

    private LogInfo logInfo;
    private User user;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new LogInfoServiceImpl(logInfoRepository, userRepository);

        user = new User("thaolt15@fsoft.com.vn", "123456", );

        logInfo = new LogInfo();

    }

    @Test
    void saveOrUpdatePositive() {

    }

    @Test
    void findLogEntityByUserPositive() {
        given(logInfoRepository.findLogInfoEntityByUser(userEntity)).willReturn(Optional.of(user));
    }

    @Test
    void findLogEntityByUserNegative() {
    }
}