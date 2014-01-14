package com.gwtplatform.mvp.databind.client.validation;

import javax.annotation.Nullable;

/**
 * Created at 09/09/13 02:04
 *
 * @author Danilo Reinert
 */
public class Validation {

    protected static Validation defaultInvalid = new Validation(false, null);
    protected static Validation defaultValid = new Validation(true, null);

    public static Validation invalid() {
        return defaultInvalid;
    }

    public static Validation invalid(ValidationMessage validationMessage) {
        return new Validation(false, validationMessage);
    }

    public static Validation valid() {
        return defaultValid;
    }

    public static Validation valid(ValidationMessage validationMessage) {
        return new Validation(true, validationMessage);
    }

    private final boolean valid;
    private final ValidationMessage validationMessage;

    protected Validation(boolean valid, @Nullable ValidationMessage validationMessage) {
        this.valid = valid;
        this.validationMessage = validationMessage;
    }

    public boolean isValid() {
        return valid;
    }

    @Nullable
    public ValidationMessage getValidationMessage() {
        return validationMessage;
    }
}
