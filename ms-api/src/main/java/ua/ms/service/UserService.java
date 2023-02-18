package ua.ms.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ms.configuration.security.repository.RegistrationService;
import ua.ms.entity.Role;
import ua.ms.entity.Status;
import ua.ms.entity.User;
import ua.ms.entity.dto.AuthenticationCredentialsDto;
import ua.ms.service.repository.UserRepository;
import ua.ms.util.exception.UserDuplicateException;

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
            User userToSave = new User();
            userToSave.setUsername(userCredentials.getUsername());
            userToSave.setPassword(userCredentials.getPassword());
            userToSave.setRole(Role.MASTER);
            userToSave.setStatus(Status.ACTIVE);
            return userRepository.save(userToSave);
        }

        log.debug("UserDuplicationException was thrown");
        throw new UserDuplicateException(format("Username [%s] already exists", userCredentials.getUsername()));
    }

    @Transactional(readOnly = true)
    public <T> Optional<T> findById(long id, Class<T> userClass) {
        return userRepository.findById(id, userClass);
    }
}