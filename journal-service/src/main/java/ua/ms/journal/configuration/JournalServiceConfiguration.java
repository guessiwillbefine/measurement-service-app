package ua.ms.journal.configuration;

import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.PropertyFilePropertySource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import static java.lang.String.format;

@Configuration
@NoArgsConstructor
@PropertySource("classpath:application.properties")
public class JournalServiceConfiguration {
    static {
        Logger logger = LoggerFactory.getLogger(JournalServiceConfiguration.class);
        logger.info("Journal service was initialized");
        org.apache.logging.log4j.util.PropertySource propertySource = new PropertyFilePropertySource("application.properties");
        logger.info(format("MongoDB connection with host[%s] and port[%s] was initialized",
                propertySource.getProperty("spring.data.mongodb.host"),
                propertySource.getProperty("spring.data.mongodb.port")));
    }
}