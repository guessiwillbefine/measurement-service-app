package ua.ms.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.info.Info;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.ms.configuration.security.AuthManager;
import ua.ms.configuration.security.util.JWTUtils;
import ua.ms.entity.dto.AuthenticationCredentialsDto;
import ua.ms.service.UserService;
import ua.ms.util.exception.UserValidationException;

import java.util.Map;

import static java.lang.String.format;

@Log4j2
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class UserAuthController {
    private final JWTUtils jwtUtils;
    private final UserService userService;
    private final AuthManager authenticationManager;

    @GetMapping("/login")
    public Map<String, String> authenticate(@NotNull @RequestBody AuthenticationCredentialsDto credentialsDto) {
        final String username = credentialsDto.getUsername();
        final String password = credentialsDto.getPassword();
        final UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(username, password);

        log.debug(format("Attempt to authenticate user [%s]", username));

        try {
            Authentication authenticatedUser = authenticationManager.authenticate(authenticationToken);
            String token = jwtUtils.generateToken(authenticatedUser.getName());
            return Map.of("jwt-token", token);

        } catch (BadCredentialsException exception) {
            log.debug(format("Error while authenticating user [%s] - bad credentials", username));
            throw exception;
        }
    }
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> register(@NotNull @RequestBody @Valid
                                        AuthenticationCredentialsDto credentialsDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) throw new UserValidationException("invalid credentials");
        log.debug("Attempt to register user [%s]");
        userService.register(credentialsDto);
        return Map.of("jwt-token", jwtUtils.generateToken(credentialsDto.getUsername()));
    }
}
