package ua.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import ua.ms.journal.configuration.JournalServiceConfiguration;

@EnableScheduling
@SpringBootApplication
@Import(JournalServiceConfiguration.class)
public class MsApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsApiApplication.class, args);
    }
}
