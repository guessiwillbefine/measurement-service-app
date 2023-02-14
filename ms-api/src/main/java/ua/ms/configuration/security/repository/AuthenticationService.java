package ua.ms.configuration.security.repository;

import ua.ms.entity.User;
import ua.ms.entity.dto.UserCredentialsDto;

public interface AuthenticationService {
    User loadByUsername(String username);

    User register(UserCredentialsDto userCredentials);
}
