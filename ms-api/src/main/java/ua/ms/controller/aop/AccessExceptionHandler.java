package ua.ms.controller.aop;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.ms.util.exception.AccessException;

@RestControllerAdvice
public class AccessExceptionHandler {
    @ExceptionHandler({AccessException.class})
    public ResponseEntity<String> response(AccessException e) {
        return ResponseEntity.status(403).body(e.getMessage());
    }
}
