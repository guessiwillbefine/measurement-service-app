package ua.ms.util.exception;

public abstract class FactoryException extends RuntimeException {
    FactoryException(String message) {
        super(message);
    }
}
