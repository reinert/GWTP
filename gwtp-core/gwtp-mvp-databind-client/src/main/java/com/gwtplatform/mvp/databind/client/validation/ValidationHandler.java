package com.gwtplatform.mvp.databind.client.validation;

/**
 * This interface provides callbacks to handle validation events.
 *
 * There are two possible validation events: valid and invalid.
 *
 * @author Danilo Reinert
 */
public interface ValidationHandler extends ValidationFailureHandler {

    /**
     * Called when a *valid* validation event occurs.
     *
     * @param message validation message provided by the binding
     */
    void onValidationSuccess(ValidationMessage message);
}
