package ua.ms.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ua.ms.entity.user.dto.UserDto;
import ua.ms.service.UserService;
import ua.ms.util.exception.EntityDuplicateException;
import ua.ms.util.journal.EventServiceImpl;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.ms.TestConstants.*;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test-env")
class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;
    @MockBean
    private EventServiceImpl eventService;

    @Test
    @DisplayName("invalid credentials should have not to be registered")
    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    void assertThatInvalidCredentialsWouldNotBeSaved() throws Exception {
        mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(INVALID_USER_CREDENTIALS)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("valid credentials should be saved")
    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    void validCredentialsShouldBeSaved() throws Exception {
        mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(USER_CREDENTIALS)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("login without token should return 403 forbidden")
    void testLoginWithoutToken() throws Exception {
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(USER_CREDENTIALS)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("duplicate username should return bad request")
    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    void duplicateRegistrationTest() throws Exception {
        when(userService.register(any())).thenThrow(EntityDuplicateException.class);
        mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(USER_CREDENTIALS)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("invalid credentials shouldn't be authorized")
    void authForInvalidCredShouldNotHappen() throws Exception {
        mockMvc.perform(post("/auth/_login").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(USER_CREDENTIALS)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("valid credentials and token should return 200")
    void validLoginShouldReturn200() throws Exception {
        doNothing().when(eventService).saveAuthorizationEvent(anyString());
        when(userService.loadByUsername(anyString())).thenReturn(Optional.of(USER_ENTITY));
        when(userService.loadByUsername(USER_ENTITY.getUsername(), UserDto.class)).thenReturn(Optional.of(USER_DTO));
        mockMvc.perform(post("/auth/_login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(USER_CREDENTIALS)))
                .andExpect(status().isOk());
    }
}
