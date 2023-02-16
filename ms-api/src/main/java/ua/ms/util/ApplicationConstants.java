package ua.ms.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApplicationConstants {
    @UtilityClass
    public class Validation {
        public static final Integer MIN_USERNAME_LENGTH = 8;
        public static final Integer MAX_USERNAME_LENGTH = 16;
        public static final String USERNAME_MSG = "Username must be between 8 an 16 symbols";
        public static final Integer MAX_PASSWORD_LENGTH = 16;
        public static final Integer MIN_PASSWORD_LENGTH = 8;
        public static final String PASSWORD_MSG = "Password must be between 8 an 16 symbols";
    }

    @UtilityClass
    public class Security {
        public static final String TOKEN_PREFIX = "Bearer ";
        public static final String TOKEN_HEADER_NAME = "Authorization";
    }
}
