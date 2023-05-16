package ua.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MsApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsApiApplication.class, args);
    }
}
