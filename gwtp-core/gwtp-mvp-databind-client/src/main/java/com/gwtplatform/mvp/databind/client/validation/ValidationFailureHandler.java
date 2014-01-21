package com.gwtplatform.mvp.databind.client.validation;

/**
 * Handler for invalid validation events.
 *
 * @author Danilo Reinert
 */
public interface ValidationFailureHandler {

    /**
     * Called when a *invalid* validation event occurs.
     *
     * @param message validation message for user feedback
     */
    void onValidationFailure(ValidationMessage message);
}
