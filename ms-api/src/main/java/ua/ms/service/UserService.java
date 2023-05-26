package ua.ms.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ms.configuration.security.service.RegistrationService;
import ua.ms.entity.user.AbstractUserIdentifiable;
import ua.ms.entity.user.Role;
import ua.ms.entity.user.Status;
import ua.ms.entity.user.User;
import ua.ms.entity.user.dto.AuthenticationCredentialsDto;
import ua.ms.entity.user.dto.UserDto;
import ua.ms.service.repository.UserRepository;
import ua.ms.util.exception.EntityDuplicateException;
import ua.ms.util.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Log4j2
@Service("registrationService")
@RequiredArgsConstructor
public class UserService implements RegistrationService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<User> loadByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public <T extends AbstractUserIdentifiable> Optional<T> loadByUsername(String username, Class<T> type) {
        return userRepository.findByUsername(username, type);
    }

    @Override
    @Transactional
    public User register(AuthenticationCredentialsDto userCredentials) {
        log.debug(format("Attempt to register user [%s]", userCredentials.getUsername()));
        try {
            //todo here will be method that sends mail notification some day...
            return userRepository.save(User.builder()
                    .username(userCredentials.getUsername())
                    .password(userCredentials.getPassword())
                    .role(Role.ADMIN)
                    .status(Status.ACTIVE)
                    .build());
        } catch (DataIntegrityViolationException e) {
            log.debug("UserDuplicationException was thrown");
            throw new EntityDuplicateException(format("Username [%s] already exists", userCredentials.getUsername()));
        }


    }

    @Transactional(readOnly = true)
    public <T extends AbstractUserIdentifiable> Optional<T> findById(long id, Class<T> userClass) {
        log.debug(format("Attempt to find user by his id[%d]", id));
        return userRepository.findById(id, userClass);
    }

    @Transactional(readOnly = true)
    public <T extends AbstractUserIdentifiable> List<T> findAll(Pageable pageable, Class<T> type) {
        log.debug(format("Attempt to pageable[size=%d page=%d] list",
                pageable.getPageNumber(), pageable.getPageSize()));
        return userRepository.findBy(pageable, type);
    }

    @Transactional(readOnly = true)
    public <T extends AbstractUserIdentifiable> List<T> findAll(Class<T> type) {
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
        throw new EntityNotFoundException(format("User with id[%d] wasn't found", id));
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
        throw new EntityNotFoundException(format("User [%s] wasn't found", userDto.getUsername()));
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