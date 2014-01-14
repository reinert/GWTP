package com.gwtplatform.mvp.databind.client.validation;

/**
 * Validator for non-null properties.
 *
 * Empty string are treated as invalid too.
 *
 * @author Danilo Reinert
 */
public class RequiredValidator<T, F> implements Validator<T, F> {

    private final Validation invalid;

    /**
     * Creates a {@link RequiredValidator} with given message for invalid results.
     *
     * @param message invalid message
     */
    public RequiredValidator(String message) {
        this.invalid = Validation.invalid(new ValidationMessage(message, ValidationMessage.Type.ERROR));
    }

    @Override
    public Validation validate(T object, F value) {
        return (value != null && !value.toString().isEmpty()) ? Validation.valid() : invalid;
    }
}
