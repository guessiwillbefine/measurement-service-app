package ua.ms.util.exception.response.impl;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ua.ms.util.exception.EntityException;
import ua.ms.util.exception.response.ExceptionResponseWrapper;

@Component
@NoArgsConstructor
public class ExceptionResponse implements ExceptionResponseWrapper<String, EntityException> {
    private String response;

    private ExceptionResponse(String response) {
        this.response = response;
    }

    @Override
    public ExceptionResponseWrapper<String, EntityException> of(EntityException runtimeException) {
        return new ExceptionResponse(runtimeException.getMessage());
    }

    @Override
    public String getResponse() {
        return response;
    }
}
