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
import ua.ms.entity.measure.MeasureSystem;
import ua.ms.entity.sensor.Sensor;
import ua.ms.entity.sensor.dto.SensorDto;
import ua.ms.service.repository.SensorRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test-env")
class SensorControllerTest {
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SensorRepository sensorRepository;
    private final String ADMIN_USER = "admin";

    @Test
    @DisplayName("test getting list of sensors with valid pagination params")
    void testGettingAllSensorsWithValidPagination() throws Exception {
        final String token = jwtUtils.generateToken(ADMIN_USER);
        final int size = sensorRepository.findAll().size();
        mockMvc.perform(get("/sensors")
                        .param("page", "0")
                        .param("size", String.valueOf(size))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("test getting list of sensors without pagination params")
    void testGettingAllSensorsWithoutPagination() throws Exception {
        final String token = jwtUtils.generateToken(ADMIN_USER);
        mockMvc.perform(get("/sensors")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("test receiving sensors by id")
    void testGettingSensor() throws Exception {
        final String token = jwtUtils.generateToken(ADMIN_USER);
        mockMvc.perform(get("/sensors/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("name").exists())
                .andReturn();
    }

    @Test
    @DisplayName("test receiving sensors by id that doesn't exist")
    void testGettingSensorThatDoesntExist() throws Exception {
        final String token = jwtUtils.generateToken(ADMIN_USER);
        mockMvc.perform(get("/sensors/-1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @DisplayName("test to update sensor")
    void testUpdateSensor() throws Exception {
        final Sensor sensorToUpdate = sensorRepository.findFirstById(1L);
        System.out.println(sensorToUpdate);
        final String token = jwtUtils.generateToken(ADMIN_USER);

        sensorToUpdate.setName("newTestName");
        String updatedSensor = objectMapper.writeValueAsString(sensorToUpdate);
        mockMvc.perform(patch("/sensors/" + sensorToUpdate.getId())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedSensor))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("name").exists())
                .andReturn();
    }

    @Test
    @DisplayName("test to update sensor that doesn't exist")
    void testUpdateSensorThatDoesntExist() throws Exception {
        final String token = jwtUtils.generateToken(ADMIN_USER);
        mockMvc.perform(patch("/sensors/-1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @DisplayName("test to create sensor")
    void testCreateSensor() throws Exception {
        final SensorDto sensor = SensorDto.builder()
                .name("newSensorTest")
                .measureSystem(MeasureSystem.VOLT)
                .build();
        final String token = jwtUtils.generateToken(ADMIN_USER);

        String sensorToCreate = objectMapper.writeValueAsString(sensor);
        mockMvc.perform(post("/sensors/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sensorToCreate)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("measureSystem").exists())
                .andReturn();
    }

    @Test
    @DisplayName("test to delete sensor")
    void testDeleteSensor() throws Exception {
        final String token = jwtUtils.generateToken(ADMIN_USER);

        mockMvc.perform(delete("/sensors/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("name").exists())
                .andReturn();
    }

    @Test
    @DisplayName("test to delete sensor that doesn't exist")
    void testDeleteSensorThatDoesntExist() throws Exception {
        final String token = jwtUtils.generateToken(ADMIN_USER);

        mockMvc.perform(delete("/sensors/-1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound())
                .andReturn();
    }
}
