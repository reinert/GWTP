package com.gwtplatform.mvp.databind.client.validation;

/**
 * This interface provides callbacks to handle validation events.
 *
 * There are two possible validation events: valid and invalid.
 *
 * @author Danilo Reinert
 */
public interface ValidationHandler<T, F> extends InvalidValueHandler<T, F> {

    /**
     * Called when a *valid* validation event occurs.
     *
     * @param object model
     * @param value flushed value
     * @param message validation message provided by the binding
     */
    void onValidValue(T object, F value, ValidationMessage message);
}
