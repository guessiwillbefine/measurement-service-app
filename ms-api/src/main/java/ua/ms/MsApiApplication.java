package ua.ms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ua.ms.configuration.security.util.JWTUtils;

@SpringBootApplication
public class MsApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsApiApplication.class, args);

    }
}
