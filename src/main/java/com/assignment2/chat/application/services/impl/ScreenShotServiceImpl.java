package com.assignment2.chat.application.services.impl;

import com.assignment2.chat.application.entities.ScreenShotEntity;
import com.assignment2.chat.application.entities.UserEntity;
import com.assignment2.chat.application.requests.ScreenShotRequest;
import com.assignment2.chat.application.response.ScreenShotResponse;
import com.assignment2.chat.application.repositories.ScreenShotRepository;
import com.assignment2.chat.application.repositories.UserRepository;
import com.assignment2.chat.application.services.ScreenShotService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class ScreenShotServiceImpl implements ScreenShotService {

    protected final ScreenShotRepository screenShotRepository;
    protected final UserRepository userRepository;

    @Override
    @Transactional
    public ScreenShotEntity save(ScreenShotEntity entity) {
        ScreenShotEntity savedEntity =  screenShotRepository.save(entity);
        log.debug("Saved screenshot: " + savedEntity);
        return savedEntity;
    }

    @Override
    public ScreenShotResponse save(ScreenShotRequest request) {

            ScreenShotEntity detachedEntity = new ScreenShotEntity();
            detachedEntity.setUser(request.getUser());
            detachedEntity.setScreenshot(request.getFile());
            detachedEntity.setCreatedAt(new Date(System.currentTimeMillis()));

            ScreenShotEntity savedEntity = save(detachedEntity);
            ScreenShotResponse response = new ScreenShotResponse();
            response.setUsername(savedEntity.getUser().getUsername());
            response.setFile(savedEntity.getScreenshot());
            return response;

    }

    @Override
    public List<ScreenShotResponse> findByUserId(Long userId) {
            UserEntity user = userRepository.findById(userId).get();

            ScreenShotResponse response = new ScreenShotResponse();
            List<ScreenShotResponse> responses = new ArrayList<>();
            List<Optional<ScreenShotEntity>> entities = screenShotRepository.findScreenShotEntitiesByUser(user);

            for (Optional<ScreenShotEntity> entity : entities){
                response.setUsername(entity.get().getUser().getUsername());
                response.setFile(entity.get().getScreenshot());
                responses.add(response);
            }
            return responses;
    }
}
