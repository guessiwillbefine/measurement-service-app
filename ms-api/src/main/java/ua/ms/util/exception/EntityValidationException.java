package ua.ms.util.exception;

import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
public class EntityValidationException extends EntityException {
    private BindingResult bindingResult;

    @Deprecated(forRemoval = true)
    public EntityValidationException(String msg) {
        super(msg);
    }

    public EntityValidationException(BindingResult bindingResult) {
        super();
        this.bindingResult = bindingResult;
    }
}
