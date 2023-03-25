package ua.ms.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ua.ms.configuration.security.util.JWTUtils;
import ua.ms.entity.user.Role;
import ua.ms.entity.user.User;
import ua.ms.service.repository.UserRepository;

import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test-env")
@Transactional
class UserControllerTest {
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    private final String ADMIN_USER = "admin";

    @Test
    @DisplayName("test receiving users by id")
    void testGettingUser() throws Exception {
        final String token = jwtUtils.generateToken(ADMIN_USER);
        mockMvc.perform(get("/users/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("username").exists())
                .andExpect(jsonPath("status").exists())
                .andExpect(jsonPath("role").exists())
                .andReturn();
    }
    @Test
    @DisplayName("test receiving users by id that doesn't exist")
    void testGettingUserThatDoesntExist() throws Exception {
        final String token = jwtUtils.generateToken(ADMIN_USER);
        mockMvc.perform(get("/users/-1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @DisplayName("test deleting users by id")
    void testDeletingUser() throws Exception {
        final String token = jwtUtils.generateToken(ADMIN_USER);
        mockMvc.perform(delete("/users/6")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("username").exists())
                .andExpect(jsonPath("status").exists())
                .andExpect(jsonPath("role").exists());
    }

    @Test
    @DisplayName("test deleting users by id without authorities")
    void testDeletingUserWithoutAuthorities() throws Exception {
        final User workerUser = userRepository.findFirstByRole(Role.WORKER);
        final String token = jwtUtils.generateToken(workerUser.getUsername());
        mockMvc.perform(delete("/users/6")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("test updating users")
    void testUpdating() throws Exception {
        final User adminUser = userRepository.findFirstByRole(Role.WORKER);
        final String token = jwtUtils.generateToken(adminUser.getUsername());

        adminUser.setLastName("newName");
        adminUser.setFirstName("newName");
        String updatedUser = objectMapper.writeValueAsString(adminUser);

        mockMvc.perform(patch("/users/" + adminUser.getId())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedUser))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("username").exists())
                .andExpect(jsonPath("status").exists())
                .andExpect(jsonPath("role").exists());
    }

    @Test
    @DisplayName("test updating users with invalid params")
    void testUpdatingWithInvalidParams() throws Exception {
        final User workerUser = userRepository.findFirstByRole(Role.ADMIN);
        final String token = jwtUtils.generateToken(workerUser.getUsername());

        workerUser.setLastName("");
        workerUser.setFirstName("");
        String updatedUser = objectMapper.writeValueAsString(workerUser);

        mockMvc.perform(patch("/users/" + workerUser.getId())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedUser))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("test pagination users")
    void testPagination() throws Exception {
        final User adminUser = userRepository.findFirstByRole(Role.WORKER);
        final String token = jwtUtils.generateToken(adminUser.getUsername());
        final int userAmount = userRepository.findAll().size();
        final int size = new Random().nextInt(1, userAmount);

        mockMvc.perform(get("/users/search")
                        .param("page", "1")
                        .param("size", String.valueOf(size))
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
    
    @Test
    @DisplayName("test pagination users with invalid params")
    void testInvalidParamsPagination() throws Exception {
        final User adminUser = userRepository.findFirstByRole(Role.WORKER);
        final String token = jwtUtils.generateToken(adminUser.getUsername());

        mockMvc.perform(get("/users/search")
                        .param("page", "0")
                        .param("size", "-1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/users/search")
                        .param("page", "-1")
                        .param("size", "10")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest());
    }

}