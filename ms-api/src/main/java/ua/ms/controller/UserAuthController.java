package ua.ms.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import ua.ms.configuration.security.AuthManager;
import ua.ms.configuration.security.repository.AuthenticationService;
import ua.ms.configuration.security.util.JWTUtils;
import ua.ms.entity.User;
import ua.ms.entity.dto.UserCredentialsDto;

import java.util.Map;

import static java.lang.String.format;

@Log4j2
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserAuthController {
    private final JWTUtils jwtUtils;
    private final AuthenticationService authenticationService;
    private final AuthManager authenticationManager;

    @GetMapping("/login")
    public User authenticate(final @NotNull @RequestBody UserCredentialsDto credentialsDto) {
        final String username = credentialsDto.getUsername();
        final UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(username, credentialsDto.getPassword());
        log.debug(format("Attempt to authenticate user [%s]", username));
        try {
            authenticationManager.authenticate(authenticationToken);
            return authenticationService.loadByUsername(username);
        } catch (BadCredentialsException exception) {
            log.debug(format("Error while authenticating user [%s] - bad credentials", username));
            throw exception;
        }
    }

    @PostMapping("/register")
    public Map<String,String> register(final @RequestBody @NotNull UserCredentialsDto credentialsDto) {
        log.debug("Attempt to register user [%s]");
        authenticationService.register(credentialsDto);
        String token = jwtUtils.generateToken(credentialsDto.getUsername());
        return Map.of("token", token);
        //idk the correct way to response after successful registration yet, so we will return token in response for now *shrug*
        //todo
    }
}
