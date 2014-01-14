package com.gwtplatform.mvp.databind.client.validation;

/**
 * Handler for invalid validation events.
 *
 * @author Danilo Reinert
 */
public interface InvalidValueHandler<T, F> {

    /**
     * Called when a *invalid* validation event occurs.
     *
     * @param object model
     * @param value value that was flushed
     * @param message validation message for user feedback
     */
    void onInvalidValue(T object, F value, ValidationMessage message);
}
