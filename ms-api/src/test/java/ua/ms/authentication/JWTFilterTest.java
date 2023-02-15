package ua.ms.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static ua.ms.TestConstants.USER_CREDENTIALS;

@ActiveProfiles("test-env")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class JWTFilterTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void emptyBearerShouldReturnException() {
        assertThrows(ServletException.class, ()-> mockMvc.perform(get("/auth/login")
                        .header("Authorization", "Bearer  ")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(USER_CREDENTIALS))));
    }
    @Test
    void NonValidBearerShouldReturnException() {
        assertThrows(ServletException.class, ()-> mockMvc.perform(get("/auth/login")
                        .header("Authorization", "Bearer some_invalid_token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(USER_CREDENTIALS))));
    }
}
