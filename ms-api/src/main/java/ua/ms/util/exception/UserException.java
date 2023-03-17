package ua.ms.util.exception;

public abstract class UserException extends RuntimeException {
    protected UserException(String message) {
        super(message);
    }
}
