package ua.ms.configuration.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import ua.ms.configuration.security.repository.RegistrationService;
import ua.ms.entity.User;

import java.util.Collections;
import java.util.Optional;

@Log4j2
@Component
@RequiredArgsConstructor
public class AuthManager implements AuthenticationManager {

    private final RegistrationService registrationService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String username = authentication.getName();
        final Optional<User> personDetails = registrationService.loadByUsername(username);
        final String password = authentication.getCredentials().toString();

        if (personDetails.isPresent()) {
            User userCredentials = personDetails.get();
            if (password.equals(userCredentials.getPassword())) {
                log.debug("Authentication successful");
                return new UsernamePasswordAuthenticationToken(username, password,
                        Collections.emptyList());
            }
        }
        log.debug("Authentication refused, BadCredentialsException was thrown");
        throw new BadCredentialsException("Incorrect credentials");
    }
}