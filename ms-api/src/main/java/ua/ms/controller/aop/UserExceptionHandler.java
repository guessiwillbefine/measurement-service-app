package ua.ms.controller.aop;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.ms.util.exception.ExceptionResponse;
import ua.ms.util.exception.UserDuplicateException;
import ua.ms.util.exception.UserValidationException;

@RestControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(UserValidationException.class)
    public ResponseEntity<ExceptionResponse> response(UserValidationException e) {
        return ResponseEntity.badRequest().body(ExceptionResponse.of(e));
    }
    @ExceptionHandler(UserDuplicateException.class)
    public ResponseEntity<ExceptionResponse> response(UserDuplicateException e) {
        return ResponseEntity.badRequest().body(ExceptionResponse.of(e));
    }
}
