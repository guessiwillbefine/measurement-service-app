package ua.ms.util.exception.response.impl;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ua.ms.util.exception.response.ExceptionResponseWrapper;

/**
 * implementation that can be used to handle IllegalArgumentException and wrap in to make HTTP  response
 * @see ua.ms.util.exception.response.ExceptionResponseWrapper
 */
@Component
@NoArgsConstructor
public class IllegalArgsResponse implements ExceptionResponseWrapper<String, IllegalArgumentException> {
    private String response;

    @Override
    public ExceptionResponseWrapper<String, IllegalArgumentException> of(IllegalArgumentException exception) {
        this.response = exception.getMessage();
        return this;
    }

    @Override
    public String getResponse() {
        if (response == null) {
            throw new IllegalStateException("the handler must first get an exception, on the basis of which the response body will be generated");
        }
        return response;
    }
}
