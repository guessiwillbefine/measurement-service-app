package ua.ms.authentication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ua.ms.configuration.security.AuthManager;
import ua.ms.service.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static ua.ms.TestConstants.USER_ENTITY;

@SpringBootTest
@ActiveProfiles("test-env")
@Transactional
class AuthManagerTest {
    @Autowired
    private AuthManager authManager;
    @Autowired
    private UserRepository userRepository;
    @Mock
    private Authentication authentication;

    @BeforeEach
    public void mockAuthentication() {
        when(authentication.getName()).thenReturn(USER_ENTITY.getUsername());
        when(authentication.getCredentials()).thenReturn(USER_ENTITY.getPassword());
    }

    @Test
    @DisplayName("valid credentials should be authenticated")
    void testAuthenticationForValidCredentials() {
        userRepository.save(USER_ENTITY);
        assertTrue(authManager.authenticate(authentication).isAuthenticated());
    }

    @Test
    @DisplayName("non existing credentials should throw exception")
    void testAuthenticationForNonPresentCredentials() {
        assertThrows(BadCredentialsException.class, () ->
                authManager.authenticate(authentication));
    }

    @Test
    @DisplayName("invalid credentials should throw exception")
    void testAuthenticationForInvalidCredentials() {
        userRepository.save(USER_ENTITY);
        when(authentication.getCredentials()).thenReturn("incorrect_password");
        assertThrows(BadCredentialsException.class, () ->
                authManager.authenticate(authentication));
    }
}
