package ua.ms.util.exception.response.impl;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ua.ms.util.exception.response.ExceptionResponseWrapper;

@Component
@NoArgsConstructor
public class IllegalStateResponse implements ExceptionResponseWrapper<String, IllegalStateException> {
    private String response;
    @Override
    public ExceptionResponseWrapper<String, IllegalStateException> of(IllegalStateException exception) {
        this.response = exception.getMessage();
        return this;
    }

    @Override
    public String getResponse() throws IllegalStateException {
        return response;
    }
}
