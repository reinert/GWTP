package com.gwtplatform.mvp.databind.client;

import com.gwtplatform.mvp.databind.client.validation.ValidationMessage;

import javax.annotation.Nullable;

/**
 * Handler for invalid validation events bound to properties.
 *
 * @author Danilo Reinert
 * @see com.gwtplatform.mvp.databind.client.validation.InvalidValueHandler
 */
public interface DatabindInvalidValueHandler {

    /**
     * Notify the view of a invalid try to update a property on the model.
     *
     * @param property model's property id
     * @param object   model object
     * @param value    updated value
     * @param message  message from presenter
     * @param <T> Model type
     * @param <F> Value type
     */
    <T, F> void onInvalidValue(String property, T object, F value, @Nullable ValidationMessage message);
}
