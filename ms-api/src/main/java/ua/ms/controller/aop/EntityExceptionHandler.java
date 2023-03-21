package ua.ms.controller.aop;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.ms.util.exception.*;
import ua.ms.util.exception.response.ExceptionResponse;

@RestControllerAdvice
public class EntityExceptionHandler {
    @ExceptionHandler({EntityValidationException.class})
    public ResponseEntity<ExceptionResponse> validationExceptionResponse(RuntimeException e) {
        return ResponseEntity.badRequest().body(ExceptionResponse.of(e));
    }

    @ExceptionHandler({EntityDuplicateException.class})
    public ResponseEntity<ExceptionResponse> duplicateExceptionResponse(RuntimeException e) {
        return ResponseEntity.badRequest().body(ExceptionResponse.of(e));
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ExceptionResponse> notFoundResponse(RuntimeException e) {
        return ResponseEntity.status(404).body(ExceptionResponse.of(e));
    }
}
