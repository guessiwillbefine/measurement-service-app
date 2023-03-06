package ua.ms.controller.aop;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.ms.util.exception.*;
import ua.ms.util.exception.response.ExceptionResponse;

@RestControllerAdvice
public class EntityExceptionHandler {
    @ExceptionHandler({UserValidationException.class, FactoryValidationException.class, SensorValidationException.class})
    public ResponseEntity<ExceptionResponse> validationExceptionResponse(RuntimeException e) {
        return ResponseEntity.badRequest().body(ExceptionResponse.of(e));
    }
    @ExceptionHandler({UserDuplicateException.class, FactoryDuplicateException.class, SensorDuplicateException.class})
    public ResponseEntity<ExceptionResponse> duplicateExceptionResponse(RuntimeException e) {
        return ResponseEntity.badRequest().body(ExceptionResponse.of(e));
    }
    @ExceptionHandler({UserNotFoundException.class, FactoryNotFoundException.class, SensorNotFoundException.class})
    public ResponseEntity<ExceptionResponse> notFoundResponse(RuntimeException e) {
        return ResponseEntity.status(404).body(ExceptionResponse.of(e));
    }
}
