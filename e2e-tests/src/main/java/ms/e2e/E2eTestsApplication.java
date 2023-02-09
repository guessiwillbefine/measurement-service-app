package ms.e2e;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ua.ms.MsApiApplication;

@SpringBootApplication
public class E2eTestsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsApiApplication.class, args);
    }

}
