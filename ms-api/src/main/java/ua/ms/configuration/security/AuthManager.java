package ua.ms.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ua.ms.configuration.security.repository.AuthenticationService;
import ua.ms.service.UserService;

import java.util.Collections;

@Component
public class AuthManager implements AuthenticationManager {

    private final AuthenticationService personDetailsService;

    @Autowired
    public AuthManager(UserService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String username = authentication.getName();
        final UserDetails personDetails = personDetailsService.loadByUsername(username);
        final String password = authentication.getCredentials().toString();

        if (password.equals(personDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(personDetails, password,
                    Collections.emptyList());
        }

        throw new BadCredentialsException("Incorrect password");
    }

}