package ru.lednyov.lib.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

public class AuthorValidationErrorBuilder {

    public static AuthorValidationError fromBindingErrors(Errors errors) {
        AuthorValidationError error = new AuthorValidationError("Validation failed. " + errors.getErrorCount() + " error(s)");
        for (ObjectError objectError : errors.getAllErrors()) {
            error.addValidationError(objectError.getDefaultMessage());
        }
        return error;
    }
}
