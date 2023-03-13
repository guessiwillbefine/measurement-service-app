package ua.ms.util.exception;

public abstract class SensorException extends RuntimeException {
    protected SensorException(String message) {
        super(message);
    }
}
