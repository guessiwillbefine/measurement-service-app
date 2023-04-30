package ua.ms.controller.aop;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.ms.util.exception.response.ExceptionResponseWrapper;

@RestControllerAdvice
@RequiredArgsConstructor
public class StateExceptionHandler {
    private final ExceptionResponseWrapper<String, IllegalStateException> illegalStateWrapper;
    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponseWrapper<String, IllegalStateException> response(IllegalStateException e) {
        return illegalStateWrapper.of(e);
    }
}
