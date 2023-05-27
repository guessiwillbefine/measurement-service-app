package ua.ms.util;

import lombok.experimental.UtilityClass;

/*
    I don't know why, but if you use wrapper classes for numbers everything will fail
    so, if u need number constants pls use int, double etc.
 */
@UtilityClass
public class ApplicationConstants {
    @UtilityClass
    public static final class Validation {
        public static final int MIN_USERNAME_LENGTH = 5;
        public static final int MAX_USERNAME_LENGTH = 25;
        public static final String USERNAME_MSG =
                "Username must be between " + MIN_USERNAME_LENGTH + " and " + MAX_USERNAME_LENGTH + " symbols";
        public static final int MIN_NAME_LENGTH = 2;
        public static final int MAX_NAME_LENGTH = 25;
        public static final String NAME_MSG =
                "Name and surname must be between " + MIN_NAME_LENGTH + " and " + MAX_NAME_LENGTH + " symbols";
        public static final int MAX_PASSWORD_LENGTH = 25;
        public static final int MIN_PASSWORD_LENGTH = 8;
        public static final String PASSWORD_MSG =
                "Password must be between " + MIN_PASSWORD_LENGTH + " and " + MAX_PASSWORD_LENGTH + " symbols";
        public static final String EMAIL_REGEXP = "^([\\w-\\.]+)@([\\w-]+\\.)+[\\w-]{2,4}$";
        public static final String EMAIL_MSG = "Invalid email";
        public static final String SENSOR_NAME_MSG =
                "Sensor name must be between " + MIN_NAME_LENGTH + " and " + MAX_NAME_LENGTH + " symbols";
        public static final int MAX_FACTORY_NAME_LENGTH = 40;
        public static final String FACTORY_NAME_MSG = "Factory name must be between 0 and " + MAX_FACTORY_NAME_LENGTH;
        public static final int MAX_MACHINE_MODEL_LENGTH = 20;
        public static final int MAX_MACHINE_NAME_LENGTH = 20;
        public static final String MACHINE_NAME_MSG = "Machine name must be between 0 and " + MAX_MACHINE_NAME_LENGTH;
        public static final String MACHINE_MODEL_MSG = "Machine model must be between 0 and " + MAX_MACHINE_MODEL_LENGTH;
        public static final String MACHINE_FACTORY_MSG = "Choose machine factory";

    }

    @UtilityClass
    public static final class Security {
        public static final String TOKEN_PREFIX = "Bearer ";
        public static final String TOKEN_HEADER_NAME = "Authorization";
        public static final String JWT_TOKEN_RESPONSE_KEY = "token";
    }

    @UtilityClass
    public static final class Mail {
        public static final String MESSAGE_TEMPLATE = """
                [Warning] Current state of sensor[%s] is not stable, please check if everything is alright
                
                Machine model : %s
                Machine name : %s
                Sensor model : %s
                last measured value: %.2f
                critical value (configuration) : %.2f
                
                You can stop machine manually from our web-service
                
                ________________________________________________________________________________________________
                measurement-service-app
        """;
    }
}
