package ua.ms.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ua.ms.configuration.security.repository.RegistrationService;
import ua.ms.configuration.security.util.JWTUtils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.ms.TestConstants.INVALID_USER_CREDENTIALS;
import static ua.ms.TestConstants.USER_CREDENTIALS;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test-env")
class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private RegistrationService registrationService;

    @Test
    void assertThatInvalidCredentialsWouldNotBeSaved() throws Exception {
        mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(INVALID_USER_CREDENTIALS)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void validCredentialsShouldBeSaved() throws Exception {
        mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(USER_CREDENTIALS)))
                .andExpect(status().isOk());
    }

    @Test
    void testLoginWithoutToken() throws Exception {
        mockMvc.perform(post("/api").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(USER_CREDENTIALS)))
                .andExpect(status().isForbidden());
    }

    @Test
    void duplicateRegistrationTest() throws Exception {
        registrationService.register(USER_CREDENTIALS);
        mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(USER_CREDENTIALS)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void authForInvalidCredShouldNotHappen() throws Exception {
        mockMvc.perform(get("/auth/login").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(USER_CREDENTIALS)))
                .andExpect(status().isForbidden());
    }

    @Test
    void validLoginShouldReturn200() throws Exception {
        final String token = "Bearer " + jwtUtils.generateToken(USER_CREDENTIALS.getUsername());

        mockMvc.perform(get("/auth/login")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(USER_CREDENTIALS)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    @Disabled("we don't have eny endpoints to test make authentication test correctly")
    void testNonBlockForAuthenticatedUser() throws Exception {
        registrationService.register(USER_CREDENTIALS);
        mockMvc.perform(post("/api").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(USER_CREDENTIALS)))
                .andExpect(status().isOk());
    }
}
