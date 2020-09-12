package com.assignment2.chat.application.repositories;

import com.assignment2.chat.application.entities.RoleEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository  extends CrudRepository<RoleEntity, Long> {

    Optional<RoleEntity> findRoleEntityByName(String name);
}
