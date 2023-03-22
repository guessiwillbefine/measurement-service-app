package ua.ms.util.exception;

public abstract class EntityException extends RuntimeException {
    EntityException(String message) {
        super(message);
    }
    EntityException() {}
}
