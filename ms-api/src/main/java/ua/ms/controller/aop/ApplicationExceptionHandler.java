package ua.ms.controller.aop;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.ms.util.exception.ApplicationException;
import ua.ms.util.exception.ExceptionResponse;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ExceptionResponse> response(ApplicationException e) {
        return ResponseEntity.internalServerError().body(ExceptionResponse.of(e));
    }
}
