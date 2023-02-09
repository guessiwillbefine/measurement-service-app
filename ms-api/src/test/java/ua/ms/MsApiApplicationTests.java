package ua.ms;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MsApiApplicationTests {

    @LocalServerPort
    private Integer port;
    @Test
    void contextLoads() {
        assertNotNull(port);
    }

}
