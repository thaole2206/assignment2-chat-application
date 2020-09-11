package com.assignment2.chat.application.services;

import com.assignment2.chat.application.entities.ScreenShotEntity;
import com.assignment2.chat.application.requests.ScreenShotRequest;
import com.assignment2.chat.application.response.ScreenShotResponse;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface ScreenShotService {

    ScreenShotEntity save(ScreenShotEntity entity);

    ScreenShotResponse save(ScreenShotRequest request);

    List<ScreenShotResponse> findByUserId(Long userId);
}
