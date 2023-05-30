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
import ua.ms.entity.work_shift.dto.WorkShiftDto;
import ua.ms.service.repository.WorkShiftRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test-env")
class WorkShiftControllerTest {
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WorkShiftRepository workShiftRepository;
    private final String ADMIN_USER = "admin";

    @Test
    @DisplayName("test getting list of work shifts with valid pagination params")
    void testGettingAllWorkShiftsWithValidPagination() throws Exception {
        final String token = jwtUtils.generateToken(ADMIN_USER);
        final int size = workShiftRepository.findAll().size();
        mockMvc.perform(get("/work_shifts")
                        .param("page", "0")
                        .param("size", String.valueOf(size))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("test getting list of work shifts without pagination params")
    void testGettingAllWorkShiftsWithoutPagination() throws Exception {
        final String token = jwtUtils.generateToken(ADMIN_USER);
        mockMvc.perform(get("/work_shifts")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("test getting list of work shifts by id with valid pagination params")
    void testGettingAllWorkShiftsByWorkerWithValidPagination() throws Exception {
        final String token = jwtUtils.generateToken(ADMIN_USER);
        final int size = workShiftRepository.findAll().size();
        mockMvc.perform(get("/work_shifts/worker/1")
                        .param("page", "0")
                        .param("size", String.valueOf(size))
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("test getting list of work shifts by id without pagination params")
    void testGettingAllWorkShiftsByWorkerWithoutPagination() throws Exception {
        final String token = jwtUtils.generateToken(ADMIN_USER);
        mockMvc.perform(get("/work_shifts/worker/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isBadRequest());
    }

//    @Test
//    @DisplayName("test to create work shift")
//    void testCreateWorkShift() throws Exception {
//        final WorkShiftDto workShiftDto = WorkShiftDto.builder()
//                .machineId(1L)
//                .workerId(1L)
//                .build();
//        final String token = jwtUtils.generateToken(ADMIN_USER);
//
//        String workShiftToCreate = objectMapper.writeValueAsString(workShiftDto);
//        System.out.println(workShiftToCreate);
//        mockMvc.perform(post("/work_shifts/create")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(workShiftToCreate)
//                        .header("Authorization", "Bearer " + token))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("workerId").exists())
//                .andExpect(jsonPath("machineId").exists())
//                .andReturn();
//    }

    @Test
    @DisplayName("test to update work shift")
    void testUpdateWorkShiftThatDoesntExist() throws Exception {
        final String token = jwtUtils.generateToken(ADMIN_USER);
        final WorkShiftDto workShiftDto = WorkShiftDto.builder()
                .machineId(0L)
                .workerId(0L)
                .build();
        String workShiftToCreate = objectMapper.writeValueAsString(workShiftDto);

        mockMvc.perform(patch("/work_shifts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(workShiftToCreate)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound())
                .andReturn();
    }
}
