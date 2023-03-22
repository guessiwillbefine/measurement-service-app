package ua.ms.controller.aop;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.ms.util.exception.response.ExceptionResponseWrapper;
import ua.ms.util.exception.response.impl.ExceptionResponse;
import java.util.NoSuchElementException;

@RestControllerAdvice
@RequiredArgsConstructor
public class ArgsExceptionHandler {
    private final ExceptionResponseWrapper<String, IllegalArgumentException> illegalArgsWrapper;
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponseWrapper<String, IllegalArgumentException> response(IllegalArgumentException e) {
        return illegalArgsWrapper.of(e);
    }
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ExceptionResponse> response() {
        return ResponseEntity.notFound().build();
    }
}
