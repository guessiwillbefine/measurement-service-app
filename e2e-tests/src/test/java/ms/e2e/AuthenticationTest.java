package ms.e2e;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import ua.ms.MsApiApplication;
import ua.ms.entity.user.dto.AuthenticationCredentialsDto;
import ua.ms.util.journal.EventServiceImpl;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.ms.util.ApplicationConstants.Security.JWT_TOKEN_RESPONSE_KEY;

@ActiveProfiles("test-env")
@Transactional
@SpringBootTest(classes = MsApiApplication.class)
@AutoConfigureMockMvc
class AuthenticationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private EventServiceImpl eventJournalService;

    @Test
    @DisplayName("authentication process test")
    void registrationScenarioTest() throws Exception {
        final AuthenticationCredentialsDto credentialsDto = AuthenticationCredentialsDto.builder()
                .username("admin").password("admin").build();
        final String credentialsJson = objectMapper.writeValueAsString(credentialsDto);

        var response = mockMvc.perform(post("/auth/_login")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(credentialsJson))
                .andExpect(status().isOk()).andReturn();

        String loginResponse = response.getResponse().getContentAsString();
        var responseBody = objectMapper.readValue(loginResponse, Map.class);
        Object tokenValue = responseBody.get("token");

        final AuthenticationCredentialsDto newCredentialsDto = AuthenticationCredentialsDto.builder()
                .username("newUser").password("newUserPass").build();
        final String newCredentialsJson = objectMapper.writeValueAsString(newCredentialsDto);

        var response2 = mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newCredentialsJson)
                .header("Authorization", "Bearer " + tokenValue))
                .andExpect(status().isCreated())
                .andExpect(jsonPath(JWT_TOKEN_RESPONSE_KEY).exists())
                .andReturn();

        String responseBody2 = response2.getResponse().getContentAsString();
        var token = objectMapper.readValue(responseBody2, Map.class);

        Object tokenValue2 = token.get(JWT_TOKEN_RESPONSE_KEY);
        assertThat(tokenValue).isNotNull();

        mockMvc.perform(post("/auth/_login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newCredentialsJson)
                        .header("Authorization", "Bearer " + tokenValue2))
                .andExpect(status().isOk());
    }
}

