package ua.ms.util.exception.response;

public interface ExceptionResponseWrapper<T, E> {
    ExceptionResponseWrapper<T, E> of(E exception);
    T getResponse();
}
