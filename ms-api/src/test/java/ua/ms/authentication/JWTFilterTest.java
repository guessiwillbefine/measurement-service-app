package ua.ms.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ua.ms.configuration.security.util.JWTUtils;
import ua.ms.service.entity.impl.UserServiceImpl;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static ua.ms.TestConstants.USER_CREDENTIALS;
import static ua.ms.TestConstants.USER_ENTITY;

@ActiveProfiles("test-env")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class JWTFilterTest {
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserServiceImpl registrationService;

    @Test
    @DisplayName("empty Bearer should throw exception")
    void emptyBearerShouldReturnException() {
        assertThrows(ServletException.class, () -> mockMvc.perform(get("/auth/login")
                .header("Authorization", "Bearer  ")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(USER_CREDENTIALS))));
    }

    @Test
    @DisplayName("invalid token should throw exception")
    void NonValidBearerShouldReturnException() {
        assertThrows(ServletException.class, () -> mockMvc.perform(get("/auth/login")
                .header("Authorization", "Bearer some_invalid_token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(USER_CREDENTIALS))));
    }

    @Test
    @DisplayName("valid credentials should be authorized")
    void validAuthorizationTest() {
        String token = jwtUtils.generateToken(USER_CREDENTIALS.getUsername());
        when(registrationService.loadByUsername(anyString())).thenReturn(Optional.of(USER_ENTITY));
        assertDoesNotThrow(() -> mockMvc.perform(get("/auth/login")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(USER_CREDENTIALS))));
    }
}
