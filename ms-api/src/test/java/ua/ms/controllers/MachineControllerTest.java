package ua.ms.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ua.ms.entity.machine.MachineType;
import ua.ms.entity.machine.dto.MachineDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ua.ms.TestConstants.MACHINE_DTO;

@SpringBootTest
@Transactional
@ActiveProfiles("test-env")
@AutoConfigureMockMvc
class MachineControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void shouldReturnAllTypes() throws Exception {
        final var response = mockMvc.perform(get("/machines/types"))
                .andExpect(status().isOk())
                .andReturn();
        final String responseContent = response.getResponse().getContentAsString();
        MachineType[] machineTypes = objectMapper.readValue(responseContent, MachineType[].class);
        assertThat(machineTypes).hasSameSizeAs(MachineType.values());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void shouldReturnMachinesList() throws Exception {
        final var response = mockMvc.perform(get("/machines/all"))
                .andExpect(status().isOk())
                .andReturn();
        final String responseContent = response.getResponse().getContentAsString();
        Object[] machines = objectMapper.readValue(responseContent, Object[].class);
        assertThat(machines).isNotNull();
    }
    @Test
    @WithMockUser(username = "admin", password = "admin")
    void searchByIdShouldReturnEntity() throws Exception {
        final var response = mockMvc.perform(get("/machines/1"))
                .andExpect(status().isOk())
                .andReturn();
        final String responseContent = response.getResponse().getContentAsString();
        Object machine = objectMapper.readValue(responseContent, Object.class);
        assertThat(machine).isNotNull();
    }
    @Test
    @WithMockUser(username = "admin", password = "admin")
    void searchByInvalidIdShouldThrowException() throws Exception {
        mockMvc.perform(get("/machines/-123"))
                .andExpect(status().isNotFound());
    }
    @Test
    @WithMockUser(username = "admin", password = "admin", authorities = "ADMIN")
    void saveShouldReturnDto() throws Exception {
        final String dtoJson = objectMapper.writeValueAsString(MACHINE_DTO);
        System.out.println(dtoJson);
        final var response = mockMvc.perform(post("/machines").contentType(MediaType.APPLICATION_JSON).content(dtoJson))
                .andExpect(status().isCreated()).andReturn();
        final String responseContent = response.getResponse().getContentAsString();
        Object machine = objectMapper.readValue(responseContent, Object.class);
        assertThat(machine).isNotNull();
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void updateShouldReturnEntity() throws Exception {
        final MachineDto machineDto = MACHINE_DTO;
        machineDto.setType(MachineType.MELTING);
        final String dtoJson = objectMapper.writeValueAsString(machineDto);
        mockMvc.perform(patch("/machines/1").contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJson))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void updateShouldBadRequestIfValidationError() throws Exception {
        final MachineDto machineDto = MachineDto.builder().build();
        final String dtoJson = objectMapper.writeValueAsString(machineDto);
        mockMvc.perform(patch("/machines/1").contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJson))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "admin", password = "admin")
    void deletingNonExistingUserShouldThrowException() throws Exception {
        mockMvc.perform(delete("/machines/-321"))
                .andExpect(status().isNotFound());
    }
    @Test
    @WithMockUser(username = "admin", password = "admin")
    void testDeletingEntities() throws Exception {
        mockMvc.perform(delete("/machines/1"))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username = "admin", password = "admin")
    void testGettingMachineBySensor() throws Exception {
        mockMvc.perform(get("/machines?sensor=1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void testReturning404WhenSearchingMachineBySensor() throws Exception {
        mockMvc.perform(get("/machines?sensor=-2131"))
                .andExpect(status().isNotFound());
    }
}
