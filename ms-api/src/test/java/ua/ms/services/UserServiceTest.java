package ua.ms.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ua.ms.entity.user.User;
import ua.ms.entity.user.dto.UserDto;
import ua.ms.service.UserService;
import ua.ms.service.repository.UserRepository;
import ua.ms.util.exception.EntityDuplicateException;
import ua.ms.util.exception.EntityNotFoundException;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
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
    @DisplayName("test throwing exception for duplicates while registering")
    void shouldThrowDuplicate() {
        when(userRepository.save(USER_ENTITY_FOR_REGISTRATION)).thenThrow(DataIntegrityViolationException.class);
        assertThatThrownBy(() -> userService.register(USER_CREDENTIALS))
                .isInstanceOf(RuntimeException.class)
                .isInstanceOf(EntityDuplicateException.class);
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

    @Test
    @DisplayName("test returning user after deleting")
    void shouldReturnUserAfterDelete() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(USER_ENTITY));
        doNothing().when(userRepository).deleteById(anyLong());
        assertThat(userService.delete(anyLong())).isEqualTo(USER_ENTITY);
    }

    @Test
    @DisplayName("test throwing exception when user to delete is not present")
    void shouldThrowExceptionWhenDeleteIfUserIsNotPresent() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        long id = USER_DTO.getId();
        assertThatThrownBy(() -> userService.delete(id))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    @DisplayName("should return updated entity")
    void shouldReturnNewUserAfterUpdate() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(USER_ENTITY));
        User updatedUser = USER_ENTITY;
        updatedUser.setFirstName("someName");
        updatedUser.setEmail("newEmail@gmail.com");
        when(userRepository.save(any())).thenReturn(updatedUser);
        assertThat(userService.update(USER_DTO.getId(), USER_DTO)).isEqualTo(updatedUser);
    }

    @Test
    @DisplayName("test finding by id")
    void shouldReturnEntityByIdIfPresent() {
        when(userRepository.findById(anyLong(), any()))
                .thenReturn(Optional.of(USER_ENTITY));

        assertThat(userService.findById(USER_ENTITY.getId(), User.class))
                .isEqualTo(Optional.of(USER_ENTITY));
    }
    @Test
    @DisplayName("test returning empty if user is not present")
    void shouldReturnEmptyOptionalIfEntityIsNotPresent() {
        when(userRepository.findById(anyLong(), any()))
                .thenReturn(Optional.empty());

        assertThat(userService.findById(USER_ENTITY.getId(), User.class)).isEmpty();
    }
    @Test
    @DisplayName("test pageable")
    void shouldReturnListWithValidPageable() {
        when(userRepository.findBy(any(Pageable.class), any()))
                .thenReturn(Collections.emptyList());

        assertThat(userService.findAll(PageRequest.of(1,1), User.class))
                .isInstanceOf(List.class);
    }
    @Test
    @DisplayName("test returning pageable for valid params")
    void shouldReturnValidSizeWithValidPageable() {
        int size = new Random().nextInt(2,10);
        List<User> users = prepareList(size);
        when(userRepository.findBy(PageRequest.of(0, size), User.class))
                .thenReturn(users);

        assertThat(userService.findAll(PageRequest.of(0, size), User.class))
                .isInstanceOf(List.class);
    }

    private List<User> prepareList(int size) {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            users.add(USER_ENTITY);
        }
        return users;
    }
}