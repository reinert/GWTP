package com.gwtplatform.mvp.databind.client.validation;

import com.google.gwt.regexp.shared.RegExp;

/**
 * @author Danilo Reinert
 */
public class EmailValidator<T> implements Validator<T, String> {

    private static RegExp emailRegExp = RegExp.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(?:[a-zA-Z]{2,6})$");

    private final Validation invalidValidation;
    private final Validation nullValidation;
    private final boolean required;

    public EmailValidator(String message) {
        this(message, false);
    }

    public EmailValidator(String message, boolean required) {
        this(message, required, null);
    }

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
