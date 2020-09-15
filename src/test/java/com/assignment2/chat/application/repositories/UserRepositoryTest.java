package com.assignment2.chat.application.repositories;

import com.assignment2.chat.application.entities.UserEntity;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    void findUserEntityByUsernamePositive() {
        UserEntity detachedEntity = UserEntity.builder().username("username").password(    "123456").build();
        entityManager.persist(detachedEntity);
        entityManager.flush();

        Optional<UserEntity> userEntity = userRepository.findUserEntityByUsername("username");
        assertEquals(userEntity.isPresent(), true);
        assertEquals(userEntity.get().getUsername(), "username");
    }

    @Test
    void findUserEntityByUsernameNegative() {
        Optional<UserEntity> userEntity = userRepository.findUserEntityByUsername("username");
        assertEquals(userEntity.isPresent(), false);
        assertEquals(userEntity.get().getUsername(), "username");
    }
}