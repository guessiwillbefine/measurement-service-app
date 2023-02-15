package ua.ms.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import ua.ms.entity.User;
import ua.ms.service.repository.UserRepository;
import ua.ms.util.exception.UserDuplicateException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ua.ms.TestConstants.USER_ENTITY;

@DataJpaTest
@Transactional
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void returnNullForNonExistingUser() {
        assertThat(userRepository.findByUsername("bla-bla-bla")).isEmpty();
    }

    @Test
    void shouldRegisterNewUsers() {
        assertThat(userRepository.save(USER_ENTITY)).isNotNull();
    }

    @Test
    void shouldLoadUserIfExists() {
        Optional<User> saved = Optional.of(userRepository.save(USER_ENTITY));
        assertThat(userRepository.findByUsername(USER_ENTITY.getUsername())).isEqualTo(saved);
    }
}
