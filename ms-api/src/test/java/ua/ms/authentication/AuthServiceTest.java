package ua.ms.authentication;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import ua.ms.configuration.security.service.RegistrationService;
import ua.ms.entity.user.User;
import ua.ms.service.repository.UserRepository;
import ua.ms.util.exception.EntityDuplicateException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static ua.ms.TestConstants.INVALID_USER_CREDENTIALS;


@ActiveProfiles("test-env")
@SpringBootTest
class AuthServiceTest {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private RegistrationService registrationService;


    @Test
    @DisplayName("Should register new users correctly if user is valid")
    void shouldRegisterIfUsernameIsValid() {
        when(userRepository.save(any(User.class))).thenReturn(new User());
        User registered = registrationService.register(INVALID_USER_CREDENTIALS);
        assertThat(registered).isNotNull();
    }
    @Test
    @DisplayName("Should throw RuntimeException if user can't be registered")
    void shouldThrowExceptionIfDuplicate() {
        when(userRepository.save(any(User.class))).thenThrow(DataIntegrityViolationException.class);
        assertThatThrownBy(() -> registrationService.register(INVALID_USER_CREDENTIALS))
                .isInstanceOf(RuntimeException.class)
                .isInstanceOf(EntityDuplicateException.class);
    }
}
