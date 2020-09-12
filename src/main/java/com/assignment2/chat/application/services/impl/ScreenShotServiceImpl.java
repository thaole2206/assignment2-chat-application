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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class ScreenShotServiceImpl implements ScreenShotService {

    private final ScreenShotRepository screenShotRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ScreenShotEntity save(ScreenShotEntity entity) {
        ScreenShotEntity savedEntity = screenShotRepository.save(entity);
        log.debug("Saved screenshot.");//TODO:
        return savedEntity;
    }

    @Override
    public ScreenShotResponse save(ScreenShotRequest request) {
            ScreenShotEntity savedEntity = save(convertRequestToEntity(request));
            return getResponse(savedEntity);
    }

    @Override
    public List<ScreenShotResponse> findByUserId(Long userId) {
        try {
            UserEntity user = userRepository.findById(userId).get();

            List<ScreenShotResponse> responses = new ArrayList<>();
            List<Optional<ScreenShotEntity>> entities = screenShotRepository.findScreenShotEntitiesByUser(user);

            for (Optional<ScreenShotEntity> entity : entities) {
                responses.add(getResponse(entity.get()));
            }
            return responses;
        } catch (NoSuchElementException e) {
            log.debug(e.getMessage());
        }
        return null;
    }

    public ScreenShotResponse getResponse(ScreenShotEntity savedEntity){
        ScreenShotResponse response = new ScreenShotResponse();
        response.setUsername(savedEntity.getUser().getUsername());
        response.setFile(savedEntity.getScreenshot());
        return response;
    }

    public ScreenShotEntity convertRequestToEntity(ScreenShotRequest request){
        ScreenShotEntity detachedEntity = new ScreenShotEntity();
        detachedEntity.setUser(request.getUser());
        detachedEntity.setScreenshot(request.getFile());
        detachedEntity.setCreatedAt(LocalDateTime.now());

        return detachedEntity;
    }
}
