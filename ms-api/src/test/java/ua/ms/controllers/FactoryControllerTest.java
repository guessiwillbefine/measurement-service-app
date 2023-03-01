package ua.ms.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ua.ms.entity.Factory;
import ua.ms.entity.dto.FactoryDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.ms.TestConstants.FACTORY_DTO;
import static ua.ms.TestConstants.FACTORY_ENTITY;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test-env")
class FactoryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    @DisplayName("test searching by id")
    void shouldReturnEntityWhenSearchingById() throws Exception {
        mockMvc.perform(get("/factories/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("test that endpoint are forbidden for unauthorized")
    void shouldReturn403WhenSearchingByIdWithoutAuthorities() throws Exception {
        mockMvc.perform(get("/factories/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    @DisplayName("test searching for invalid id")
    void shouldReturn404WhenSearchingByInvalidId() throws Exception {
        mockMvc.perform(get("/factories/-1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    @DisplayName("test getting all factories")
    void shouldReturn200whenSearchingForList() throws Exception {
        mockMvc.perform(get("/factories/search"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("test deleting with")
    void deletingUserShouldBeForbiddenWithoutAuthorities() throws Exception {
        mockMvc.perform(delete("/factories/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    void deletingUserShouldBe200ForAdmin() throws Exception {
        mockMvc.perform(delete("/factories/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    void creatingFactoriesShouldReturn201() throws Exception {
        String factoryJson = objectMapper.writeValueAsString(FACTORY_DTO);
        mockMvc.perform(post("/factories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(factoryJson))
                .andExpect(status().isCreated());
    }
    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    void creatingInvalidFactoriesShouldReturn403() throws Exception {
        FactoryDto invalidFactory = FactoryDto.builder().name("").build();
        String invalidFactoryJson = objectMapper.writeValueAsString(invalidFactory);
        mockMvc.perform(post("/factories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidFactoryJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    void testUpdatingFactories() throws Exception {
        Factory updatedFactory = FACTORY_ENTITY;
        updatedFactory.setName("NewFactoryName");
        String factoryJson = objectMapper.writeValueAsString(updatedFactory);
        mockMvc.perform(patch("/factories/" + updatedFactory.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(factoryJson))
                .andExpect(status().isOk());
    }
}
