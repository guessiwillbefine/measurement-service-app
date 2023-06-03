package ua.ms.configuration;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;

@Log4j2
@Configuration
public class AlertConfiguration {

    @Value("${configuration.mail.username}")
    private String username;

    @Value("${configuration.mail.password}")
    private String password;

    @Value("${configuration.mail.host}")
    private String host;

    @Value("${configuration.mail.port}")
    private String port;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(parseInt(port));
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        mailSender.getJavaMailProperties().setProperty("mail.smtp.auth", "true");
        mailSender.getJavaMailProperties().setProperty("mail.smtp.starttls.enable", "true");

        if (username.isEmpty() || password.isEmpty() || host.isEmpty() || port.isEmpty()) {
            log.error("JavaMailSender will be not initialized correctly because of null properties, pls check your configurations");
        }
        else {
            log.info(format("JavaMailSender was initialized with class [%s]",
                    JavaMailSender.class.getPackageName() + JavaMailSender.class.getSimpleName()));
        }
        return mailSender;
    }
}
