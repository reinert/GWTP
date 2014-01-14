package com.gwtplatform.mvp.databind.client.validation;

import javax.annotation.Nullable;

/**
 * Interface used to validate databind flushes from view.
 *
 * You should implement one validator for each property that requires a specific validation.
 * The property is assumed to be known outside its validator.
 *
 * @author Danilo Reinert
 */
public interface Validator<T, F> {

    /**
     * Validates a value to be set in a model and returns a {@link Validation}.
     * The validation contains a flag informing if it was valid or not and a {@link ValidationMessage}
     * provided with a textual user-friendly message and a {@link ValidationMessage.Type}.
     *
     * The lack of an property's id argument does not limits its implementation because the property is
     * known by the time of binding.
     *
     * @param object model which will hold (or not) the value
     * @param value value being passed to the model
     * @return validation result
     */
    Validation validate(T object, @Nullable F value);
}
