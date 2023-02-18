package ua.ms.configuration.security.repository;

import ua.ms.entity.User;
import ua.ms.entity.dto.AuthenticationCredentialsDto;

import java.util.Optional;

public interface RegistrationService {
    Optional<User> loadByUsername(String username);
    User register(AuthenticationCredentialsDto userCredentials);
}
