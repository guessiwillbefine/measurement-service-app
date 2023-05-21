package ms.e2e;

import com.fasterxml.jackson.core.JsonProcessingException;
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

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ua.ms.util.ApplicationConstants.Security.*;

/** factory scenario tests - modifying, deleting entities. **/
@ActiveProfiles("test-env")
@Transactional
@SpringBootTest(classes = MsApiApplication.class)
@AutoConfigureMockMvc
class FactoryTest {
    private final AuthenticationCredentialsDto adminCredentials = AuthenticationCredentialsDto.builder()
            .username("admin").password("admin").build();
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EventServiceImpl eventJournalService;

    @Test
    @DisplayName("test deleting factories ")
    void testDeletingEntities() throws Exception {
        final String credentialsJson = objectMapper.writeValueAsString(adminCredentials);
        var loginResponse = mockMvc.perform(post("/auth/_login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(credentialsJson))
                .andExpect(status().isOk()).andReturn();
        final String token = getToken(loginResponse.getResponse().getContentAsString());

        var factoryResponse = mockMvc.perform(get("/factories/search")
                        .header(TOKEN_HEADER_NAME, TOKEN_PREFIX + token))
                .andExpect(status().isOk()).andReturn();

        String response = factoryResponse.getResponse().getContentAsString();
        Object[] factoriesBeforeDelete = objectMapper.readValue(response, Object[].class);
        assertThat(factoriesBeforeDelete).isNotNull();


        mockMvc.perform(delete("/factories/1")
                        .header(TOKEN_HEADER_NAME, TOKEN_PREFIX + token))
                .andExpect(status().isOk());

        var factoryResponse2 = mockMvc.perform(get("/factories/search")
                        .header(TOKEN_HEADER_NAME, TOKEN_PREFIX + token))
                .andExpect(status().isOk()).andReturn();

        String response2 = factoryResponse2.getResponse().getContentAsString();
        Object[] factoriesAfterDelete = objectMapper.readValue(response2, Object[].class);

        assertThat(factoriesAfterDelete).isNotNull();
        assertThat(factoriesBeforeDelete.length - factoriesAfterDelete.length).isEqualTo(1);
    }
    @Test
    @DisplayName("test updating factories ")
    void testUpdatingEntities() throws Exception {
        final String credentialsJson = objectMapper.writeValueAsString(adminCredentials);
        final String newName = "newName";
        var response = mockMvc.perform(post("/auth/_login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(credentialsJson))
                .andExpect(status().isOk()).andReturn();
        final String token = getToken(response.getResponse().getContentAsString());

        var factoryResponse = mockMvc.perform(get("/factories/1")
                        .header(TOKEN_HEADER_NAME, TOKEN_PREFIX + token))
                .andExpect(status().isOk()).andReturn();
        String factoryBody = factoryResponse.getResponse().getContentAsString();
        var factoryData = objectMapper.readValue(factoryBody, Map.class);

        var factoryToUpdate = new HashMap<>();
        factoryToUpdate.putAll(factoryData);
        factoryToUpdate.put("name", newName);

        String updatedFactoryJson = objectMapper.writeValueAsString(factoryToUpdate);
        var responseAfterUpdate = mockMvc.perform(patch("/factories/1")
                        .header(TOKEN_HEADER_NAME, TOKEN_PREFIX + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedFactoryJson))
                .andExpect(status().isOk())
                .andReturn();

        String updatedFactoryBody = responseAfterUpdate.getResponse().getContentAsString();
        var updatedFactory = objectMapper.readValue(updatedFactoryBody, Map.class);

        assertThat(updatedFactory.get("name"))
                .isNotNull()
                .isNotEqualTo(factoryData.get("name"))
                .isEqualTo(newName);
    }

    private String getToken(String contentAsString) throws JsonProcessingException {
        var body = objectMapper.readValue(contentAsString, Map.class);
        return body.get(JWT_TOKEN_RESPONSE_KEY).toString();
    }
}
