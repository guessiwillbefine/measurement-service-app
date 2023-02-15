package ua.ms.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ua.ms.entity.User;
import ua.ms.service.UserService;
import ua.ms.util.exception.UserDuplicateException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ua.ms.TestConstants.USER_CREDENTIALS;

@SpringBootTest
@ActiveProfiles("test-env")
@Transactional
class UserServiceTest {
    @Autowired
    private UserService userService;


    @Test
    void shouldReturnEntityIfRegistered() {
        User registeredUser = userService.register(USER_CREDENTIALS);
        assertThat(registeredUser.getUsername()).isEqualTo(USER_CREDENTIALS.getUsername());
        assertThat(registeredUser.getPassword()).isEqualTo(USER_CREDENTIALS.getPassword());
    }

    @Test
    void shouldThrowDuplicate() {
        assertThat(userService.register(USER_CREDENTIALS)).isNotNull();
        assertThatThrownBy(() -> userService.register(USER_CREDENTIALS))
                .isInstanceOf(RuntimeException.class)
                .isInstanceOf(UserDuplicateException.class);
    }
    @Test
    void shouldLoadUserIfExists() {
        Optional<User> beforeRegistration = userService.loadByUsername(USER_CREDENTIALS.getUsername());
        assertThat(beforeRegistration).isEmpty();
        userService.register(USER_CREDENTIALS);
        Optional<User> afterRegistration = userService.loadByUsername(USER_CREDENTIALS.getUsername());
        assertThat(afterRegistration).isPresent();
    }
}
