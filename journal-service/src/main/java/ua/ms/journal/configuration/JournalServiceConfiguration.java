package ua.ms.journal.configuration;

import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@NoArgsConstructor
@PropertySource("classpath:application.properties")
public class JournalServiceConfiguration {
    static {
        Logger logger = LoggerFactory.getLogger(JournalServiceConfiguration.class);
        logger.info("Journal service was initialized");
    }
}