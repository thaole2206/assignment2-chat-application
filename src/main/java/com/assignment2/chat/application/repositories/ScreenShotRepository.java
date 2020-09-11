package com.assignment2.chat.application.repositories;

import com.assignment2.chat.application.entities.ScreenShotEntity;
import com.assignment2.chat.application.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ScreenShotRepository extends CrudRepository<ScreenShotEntity, Long> {

    List<Optional<ScreenShotEntity>> findScreenShotEntitiesByUser(UserEntity user);
}
