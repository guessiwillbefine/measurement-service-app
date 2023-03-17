package ua.ms.util.exception.response;

import lombok.Getter;

@Getter
public class ExceptionResponse {
    private final String response;
    private ExceptionResponse(String response) {
        this.response = response;
    }
    public static ExceptionResponse of(RuntimeException e) {
        return new ExceptionResponse(e.getMessage());
    }
}
