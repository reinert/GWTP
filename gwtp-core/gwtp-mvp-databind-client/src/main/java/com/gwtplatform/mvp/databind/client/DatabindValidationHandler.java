package com.gwtplatform.mvp.databind.client;

import com.gwtplatform.mvp.databind.client.validation.ValidationMessage;

import javax.annotation.Nullable;

/**
 * This interface provides callbacks to handle validation events bound to properties.
 *
 * @author Danilo Reinert
 * @see com.gwtplatform.mvp.databind.client.validation.ValidationHandler
 */
public interface DatabindValidationHandler extends DatabindInvalidValueHandler {

    /**
     * Notify the view of a property updated on model.
     *
     * @param property model's property id
     * @param object   model object
     * @param value    updated value
     * @param message  message from presenter
     * @param <T> Model type
     * @param <F> Value type
     */
    <T, F> void onValidValue(String property, T object, F value, @Nullable ValidationMessage message);
}
