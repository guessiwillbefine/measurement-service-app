package ua.ms.configuration.security.service;

import ua.ms.entity.user.User;
import ua.ms.entity.user.dto.AuthenticationCredentialsDto;

import java.util.Optional;

public interface RegistrationService {
    Optional<User> loadByUsername(String username);
    User register(AuthenticationCredentialsDto userCredentials);
}
