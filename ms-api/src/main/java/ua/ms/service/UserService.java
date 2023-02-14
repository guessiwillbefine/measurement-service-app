package ua.ms.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.ms.configuration.security.repository.AuthenticationService;
import ua.ms.entity.User;
import ua.ms.entity.dto.UserCredentialsDto;
import ua.ms.service.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService implements AuthenticationService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public User loadByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public User register(UserCredentialsDto userCredentials) {
        User byUsername = userRepository.findByUsername(userCredentials.getUsername());
        if (byUsername == null) {
            User userToSave = new User();
            userToSave.setUsername(userCredentials.getUsername());
            userToSave.setPassword(userCredentials.getPassword());
            return userRepository.save(userToSave);
        }
        throw new RuntimeException(); //todo make exception and handler for it
    }
}