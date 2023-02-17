package ua.ms.util;

import lombok.experimental.UtilityClass;
/*
    idk why, but if you use wrapper classes for numbers everything will fail
    so, if u need number constants pls use int, double etc.
 */
@UtilityClass
public class ApplicationConstants {
    @UtilityClass
    public class Validation {
        public static final int MIN_USERNAME_LENGTH = 8;
        public static final int MAX_USERNAME_LENGTH = 16;
        public static final String USERNAME_MSG = "Username must be between 8 an 16 symbols";
        public static final int MAX_PASSWORD_LENGTH = 16;
        public static final int MIN_PASSWORD_LENGTH = 8;
        public static final String PASSWORD_MSG = "Password must be between 8 an 16 symbols";
    }

    @UtilityClass
    public class Security {
        public static final String TOKEN_PREFIX = "Bearer ";
        public static final String TOKEN_HEADER_NAME = "Authorization";
    }
}
