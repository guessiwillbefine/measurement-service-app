package ua.ms.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ua.ms.entity.User;
import ua.ms.entity.dto.UserDto;
import ua.ms.service.UserService;
import ua.ms.service.repository.UserRepository;
import ua.ms.util.exception.UserDuplicateException;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static ua.ms.TestConstants.*;

@SpringBootTest
@ActiveProfiles("test-env")
@Transactional
class UserServiceTest {
    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;


    @Test
    @DisplayName("test returning entities if they are present")
    void shouldReturnEntityIfRegistered() {
        when(userRepository.save(any(User.class))).thenReturn(USER_ENTITY);
        User registeredUser = userService.register(USER_CREDENTIALS);
        assertThat(registeredUser.getUsername()).isEqualTo(USER_CREDENTIALS.getUsername());
        assertThat(registeredUser.getPassword()).isEqualTo(USER_CREDENTIALS.getPassword());
    }

    @Test
    @DisplayName("test throwing exception for duplicates")
    void shouldThrowDuplicate() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(USER_ENTITY));
        assertThatThrownBy(() -> userService.register(USER_CREDENTIALS))
                .isInstanceOf(RuntimeException.class)
                .isInstanceOf(UserDuplicateException.class);
    }
    @Test
    @DisplayName("existing user should be loaded")
    void shouldLoadUserIfExists() {
        Optional<User> beforeRegistration = userService.loadByUsername(USER_CREDENTIALS.getUsername());
        assertThat(beforeRegistration).isEmpty();
        userService.register(USER_CREDENTIALS);

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(USER_ENTITY));
        Optional<User> afterRegistration = userService.loadByUsername(USER_CREDENTIALS.getUsername());
        assertThat(afterRegistration).isPresent();
    }

    @Test
    @DisplayName("should return correct class type")
    void shouldLoadUserIfExistWithCorrectType() {

        Optional<User> userEntity = Optional.of(USER_ENTITY);
        when(userRepository.findByUsername(USER_CREDENTIALS.getUsername(), User.class)).thenReturn(userEntity);

        Optional<User> optionalUser = userService.loadByUsername(USER_CREDENTIALS.getUsername(), User.class);

        assertTrue(optionalUser.isPresent());
        assertThat(optionalUser.get())
                .isInstanceOf(User.class);

        Optional<UserDto> userDto = Optional.of(USER_DTO);
        when(userRepository.findByUsername(USER_CREDENTIALS.getUsername(), UserDto.class)).thenReturn(userDto);

        Optional<UserDto> optionalUserDto = userService.loadByUsername(USER_CREDENTIALS.getUsername(), UserDto.class);
        assertTrue(optionalUserDto.isPresent());
        assertThat(optionalUserDto.get())
                .isInstanceOf(UserDto.class);
    }
}
