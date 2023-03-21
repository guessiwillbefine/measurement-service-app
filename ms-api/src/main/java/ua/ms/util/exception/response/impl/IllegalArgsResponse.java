package ua.ms.util.exception.response.impl;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ua.ms.util.exception.response.ExceptionResponseWrapper;
@Component
@NoArgsConstructor
public class IllegalArgsResponse implements ExceptionResponseWrapper<String, IllegalArgumentException> {
    private String response;

    private IllegalArgsResponse(String response) {
        this.response = response;
    }

    @Override
    public ExceptionResponseWrapper<String, IllegalArgumentException> of(IllegalArgumentException exception) {
        return new IllegalArgsResponse(exception.getMessage());
    }

    @Override
    public String getResponse() {
        return response;
    }
}
