package ua.ms.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.ms.configuration.security.AuthManager;
import ua.ms.configuration.security.util.JWTUtils;
import ua.ms.entity.dto.AuthenticationCredentialsDto;
import ua.ms.entity.dto.UserDto;
import ua.ms.service.UserService;
import ua.ms.util.exception.ApplicationException;
import ua.ms.util.exception.UserValidationException;

import java.util.Map;
import java.util.Optional;

import static java.lang.String.format;

@Log4j2
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserAuthController {
    private final JWTUtils jwtUtils;
    private final UserService registrationService;
    private final AuthManager authenticationManager;

    @GetMapping("/login")
    public UserDto authenticate(@NotNull @RequestBody AuthenticationCredentialsDto credentialsDto) {
        final String username = credentialsDto.getUsername();
        final String password = credentialsDto.getPassword();
        final UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(username, password);

        log.debug(format("Attempt to authenticate user [%s]", username));

        try {
            Optional<UserDto> user = registrationService.loadByUsername(username, UserDto.class);
            Authentication authenticatedUser = authenticationManager.authenticate(authenticationToken);
            if (authenticatedUser.isAuthenticated() && user.isPresent()) {
                return user.get();
            }
            else {
                log.error("User wasn't authenticated despite of correct credentials");
                throw new ApplicationException("Something went wrong, try again later");
            }
        } catch (BadCredentialsException exception) {
            log.debug(format("Error while authenticating user [%s] - bad credentials", username));
            throw exception;
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody @NotNull @Valid
                                                            AuthenticationCredentialsDto credentialsDto,
                                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) throw new UserValidationException("invalid credentials");
        log.debug("Attempt to register user [%s]");
        registrationService.register(credentialsDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("jwt-token", jwtUtils.generateToken(credentialsDto.getUsername())));
    }
}
