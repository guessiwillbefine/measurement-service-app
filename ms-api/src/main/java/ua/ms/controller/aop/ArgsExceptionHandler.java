package ua.ms.controller.aop;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.ms.util.exception.ExceptionResponse;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class ArgsExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> response(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(ExceptionResponse.of(e));
    }
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ExceptionResponse> response() {
        return ResponseEntity.notFound().build();
    }
}
