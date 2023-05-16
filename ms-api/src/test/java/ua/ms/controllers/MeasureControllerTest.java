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
import ua.ms.entity.measure.dto.MeasureDto;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test-env")
class MeasureControllerTest {
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    private final String ADMIN_USER = "admin";

    @Test
    @DisplayName("test to get all measures by sensor id")
    void testGettingAllMeasuresBySensorId() throws Exception {
        final String token = jwtUtils.generateToken(ADMIN_USER);
        mockMvc.perform(get("/measures/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("test to create measure")
    void testCreatingMeasure() throws Exception {
        final String token = jwtUtils.generateToken(ADMIN_USER);
        final MeasureDto measureDto = MeasureDto.builder()
                .value(27.7)
                .sensorId(1)
                .build();
        System.out.println(measureDto);
        String measureDtoJson = objectMapper.writeValueAsString(measureDto);

        mockMvc.perform(post("/measures")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(measureDtoJson))
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("value").exists())
                .andExpect(jsonPath("sensorId").exists())
                .andExpect(jsonPath("createdAt").exists())
                .andReturn();
    }

    @Test
    @DisplayName("test to delete all measures by sensor id")
    void testDeleteMeasures() throws Exception {
        final String token = jwtUtils.generateToken(ADMIN_USER);
        mockMvc.perform(delete("/measures/1")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

}
