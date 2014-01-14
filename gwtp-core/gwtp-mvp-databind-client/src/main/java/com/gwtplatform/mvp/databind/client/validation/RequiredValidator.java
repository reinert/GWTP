package com.gwtplatform.mvp.databind.client.validation;

/**
 * @author Danilo Reinert
 */
public class RequiredValidator<T, F> implements Validator<T, F> {

    private final Validation invalid;

    public RequiredValidator(String message) {
        this.invalid = Validation.invalid(new ValidationMessage(message, ValidationMessage.Type.ERROR));
    }

    @Override
    public Validation validate(T object, F value) {
        return (value != null && !value.toString().isEmpty()) ? Validation.valid() : invalid;
    }
}
