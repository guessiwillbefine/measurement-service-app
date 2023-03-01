package ua.ms.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ms.configuration.security.repository.RegistrationService;
import ua.ms.entity.Role;
import ua.ms.entity.Status;
import ua.ms.entity.User;
import ua.ms.entity.dto.AuthenticationCredentialsDto;
import ua.ms.entity.dto.UserDto;
import ua.ms.service.repository.UserRepository;
import ua.ms.util.exception.UserDuplicateException;
import ua.ms.util.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserService implements RegistrationService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<User> loadByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public <T> Optional<T> loadByUsername(String username, Class<T> type) {
        return userRepository.findByUsername(username, type);
    }

    @Override
    @Transactional
    public User register(AuthenticationCredentialsDto userCredentials) {
        final Optional<User> byUsername = userRepository.findByUsername(userCredentials.getUsername());
        log.debug(format("Attempt to register user [%s]", userCredentials.getUsername()));
        if (byUsername.isEmpty()) {
            //todo here will be method that sends mail notification some day...
            return userRepository.save(User.builder()
                    .username(userCredentials.getUsername())
                    .password(userCredentials.getPassword())
                    .role(Role.ADMIN)
                    .status(Status.ACTIVE)
                    .build());
        }
        log.debug("UserDuplicationException was thrown");
        throw new UserDuplicateException(format("Username [%s] already exists", userCredentials.getUsername()));
    }

    @Transactional(readOnly = true)
    public <T> Optional<T> findById(long id, Class<T> userClass) {
        log.debug(format("Attempt to find user by his id[%d]", id));
        return userRepository.findById(id, userClass);
    }

    @Transactional(readOnly = true)
    public <T> List<T> findAll(Pageable pageable, Class<T> type) {
        log.debug(format("Attempt to pageable[size=%d page=%d] list",
                pageable.getPageNumber(), pageable.getPageSize()));
        return userRepository.findBy(pageable, type);
    }

    @Transactional(readOnly = true)
    public <T> List<T> findAll(Class<T> type) {
        return userRepository.findBy(type);
    }
    @Transactional
    public User delete(long id) {
        final Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            userRepository.deleteById(id);
            return byId.get();
        }
        log.debug("User wasn't found, throwing UserNotFoundException");
        throw new UserNotFoundException(format("User with id[%d] wasn't found", id));
    }

    @Transactional
    public User update(long id, UserDto userDto) {
        final Optional<User> byUsername = userRepository.findById(id);
        if (byUsername.isPresent()) {
            User toUpdate = byUsername.get();
            User updated = updateEntityFields(toUpdate, userDto);
            return userRepository.save(updated);
        }
        log.debug("User wasn't found, throwing UserNotFoundException");
        throw new UserNotFoundException(format("User [%s] wasn't found", userDto.getUsername()));
    }

    private User updateEntityFields(User entity, UserDto dto) {
        entity.setUsername(dto.getUsername() != null ? dto.getUsername() : entity.getUsername());
        entity.setFirstName(dto.getFirstName() != null ? dto.getFirstName() : entity.getFirstName());
        entity.setLastName(dto.getLastName() != null ? dto.getLastName() : entity.getLastName());
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : entity.getStatus());
        entity.setRole(dto.getRole() != null ? dto.getRole() : entity.getRole());
        entity.setEmail(dto.getEmail() != null ? dto.getEmail() : entity.getEmail());
        return entity;
    }
}