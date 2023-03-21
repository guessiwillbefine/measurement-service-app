package ua.ms.util.exception.response.impl;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ua.ms.util.exception.EntityException;
import ua.ms.util.exception.response.ExceptionResponseWrapper;

/**
 * default implementation that handles entity exceptions
 * @see ua.ms.util.exception.response.ExceptionResponseWrapper
 */
@Component
@NoArgsConstructor
public class ExceptionResponse implements ExceptionResponseWrapper<String, EntityException> {
    private String response;

    @Override
    public ExceptionResponseWrapper<String, EntityException> of(EntityException runtimeException) {
        this.response = runtimeException.getMessage();
        return this;
    }

    @Override
    public String getResponse() {
        return response;
    }
}
