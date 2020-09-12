package com.assignment2.chat.application.services;

import com.assignment2.chat.application.entities.LogInfoEntity;
import com.assignment2.chat.application.entities.ScreenShotEntity;
import com.assignment2.chat.application.entities.UserEntity;
import com.assignment2.chat.application.repositories.ScreenShotRepository;
import com.assignment2.chat.application.repositories.UserRepository;
import com.assignment2.chat.application.requests.ScreenShotRequest;
import com.assignment2.chat.application.response.ScreenShotResponse;
import com.assignment2.chat.application.services.impl.LogInfoServiceImpl;
import com.assignment2.chat.application.services.impl.ScreenShotServiceImpl;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
@NoArgsConstructor
class ScreenShotServiceTest {

    private ScreenShotService service;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ScreenShotRepository screenShotRepository;

    private UserEntity userEntity;
    private List<Optional<ScreenShotEntity>> list;
    private final LocalDateTime date = LocalDateTime.now();
    private ScreenShotEntity screenShotEntity;
    private ScreenShotRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new ScreenShotServiceImpl(screenShotRepository, userRepository);

        String username = "memeber@gmail.com";
        byte[] arr = {1,2,3,4,5};

        userEntity = UserEntity.builder().username(username).build();
        list = new ArrayList<>();
        list.add(Optional.of(
                ScreenShotEntity.builder().screenshot(arr).user(userEntity).createdAt(date).build()
        ));
        screenShotEntity = ScreenShotEntity.builder().user(userEntity).createdAt(date).screenshot(arr).build();
        request = ScreenShotRequest.builder().file(arr).user(userEntity).build();
    }

    @Test
    void saveEntityPositve() {
        given(screenShotRepository.save(any(ScreenShotEntity.class))).willReturn(screenShotEntity);
        ScreenShotEntity savedEntity = service.save(screenShotEntity);
        assertEquals(screenShotEntity, savedEntity);
    }

    @Test
    void saveEntityNegative() {
        given(screenShotRepository.save(any(ScreenShotEntity.class))).willReturn(null);
        ScreenShotEntity savedEntity = service.save(screenShotEntity);
        assertNull(savedEntity);
    }

    @Test
    void savePositive(){
        given(screenShotRepository.save(any(ScreenShotEntity.class))).willReturn(screenShotEntity);
        ScreenShotResponse response = service.save(request);
        assertEquals(screenShotEntity.getUser().getUsername(), response.getUsername());
        assertEquals(screenShotEntity.getScreenshot(), response.getFile());
    }

    @Test
    void saveNegative(){
        given(screenShotRepository.save(any(ScreenShotEntity.class))).willReturn(null);
        assertThrows(NullPointerException.class, () -> service.save(request));
    }

    @Test
    void findByUserIdPositive() {
        given(userRepository.findById(userEntity.getId())).willReturn(Optional.of(userEntity));
        given(screenShotRepository.findScreenShotEntitiesByUser(userEntity)).willReturn(list);
        final List<ScreenShotResponse> responses = service.findByUserId(userEntity.getId());

        assertEquals(list.size(), responses.size());
        //TODO: assert more
    }

    @Test
    void findByUserIdNegative() {
        final List<ScreenShotResponse> responses = service.findByUserId(userEntity.getId());
        assertNull(responses);
    }

}