package com.assignment2.chat.application.repositories;

import com.assignment2.chat.application.entities.LogInfoEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LogInfoRepository extends CrudRepository<LogInfoEntity, Long> {

    Optional<LogInfoEntity> findLogInfoEntityByUser_Username(String username);
}
