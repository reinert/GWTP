package com.gwtplatform.mvp.databind.client.validation;

import com.google.gwt.regexp.shared.RegExp;

/**
 * Validator for textual values that should follow the email pattern.
 * Null values are optionally invalidated.
 *
 * @author Danilo Reinert
 */
public class EmailValidator<T> implements Validator<T, String> {

    private static RegExp emailRegExp = RegExp.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(?:[a-zA-Z]{2,6})$");

    private final Validation invalidValidation;
    private final Validation nullValidation;
    private final boolean required;

    /**
     * Creates a {@link EmailValidator} with given message for invalid results.
     *
     * This validator will successfully validate null values.
     *
     * @param message invalid message
     */
    public EmailValidator(String message) {
        this(message, false);
    }

    /**
     * Creates a {@link EmailValidator} with given message for invalid results.
     *
     * Null (and empty) values will be invalidated as well.
     *
     * @param message invalid message
     */
    public EmailValidator(String message, boolean required) {
        this(message, required, null);
    }

    /**
     * Creates a {@link EmailValidator} with messages for invalid email values and null values.
     * When a validation with a *null* or *empty* string occurs then an invalid {@link Validation} is returned
     * with the #nullMessage inside. Differently, if a non-empty string is given, but don't satisfy the requirements,
     * then an invalid {@link Validation} with the #invalidMessage is returned.
     *
     * @param invalidMessage invalid email message
     * @param nullMessage null value message
     */
    public EmailValidator(String invalidMessage, boolean required, String nullMessage) {
        this.required = required;
        this.invalidValidation = Validation.invalid(new ValidationMessage(invalidMessage, ValidationMessage.Type.ERROR));
        if (nullMessage != null) {
            this.nullValidation = Validation.invalid(new ValidationMessage(nullMessage, ValidationMessage.Type.ERROR));
        } else {
            this.nullValidation = this.invalidValidation;
        }
    }

    @Override
    public Validation validate(T object, String value) {
        if (value == null || value.isEmpty()) {
            if (required) {
                return nullValidation;
            } else {
                return Validation.valid();
            }
        }
        return emailRegExp.test(value) ? Validation.valid() : invalidValidation;
    }
}
