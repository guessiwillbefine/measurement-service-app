package ua.ms.util.exception;

public abstract class MachineException extends RuntimeException {
    protected MachineException(String message) {
        super(message);
    }
}
