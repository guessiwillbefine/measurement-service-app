package ua.ms.controller.aop;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.ms.util.exception.response.ExceptionResponse;
import ua.ms.util.exception.UserDuplicateException;
import ua.ms.util.exception.UserNotFoundException;
import ua.ms.util.exception.UserValidationException;

@RestControllerAdvice
public class EntityExceptionHandler {
    @ExceptionHandler(UserValidationException.class)
    public ResponseEntity<ExceptionResponse> response(UserValidationException e) {
        return ResponseEntity.badRequest().body(ExceptionResponse.of(e));
    }
    @ExceptionHandler(UserDuplicateException.class)
    public ResponseEntity<ExceptionResponse> response(UserDuplicateException e) {
        return ResponseEntity.badRequest().body(ExceptionResponse.of(e));
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> response(UserNotFoundException e) {
        return ResponseEntity.status(404).body(ExceptionResponse.of(e));
    }
}
