package com.gwtplatform.mvp.databind.client;

import com.gwtplatform.mvp.databind.client.validation.ValidationMessage;

import javax.annotation.Nullable;

/**
 * This interface provides callbacks to handle validation events bound to properties.
 *
 * @author Danilo Reinert
 * @see com.gwtplatform.mvp.databind.client.validation.ValidationHandler
 */
public interface DatabindValidationHandler {

    /**
     * Notify the view of a invalid try to update a property on the model.
     *
     * @param property model's property id
     * @param message  message from presenter
     */
    void onValidationFailure(String property, @Nullable ValidationMessage message);

    /**
     * Notify the view of a property updated on model.
     *
     * @param property model's property id
     * @param message  message from presenter
     */
    void onValidationSuccess(String property, @Nullable ValidationMessage message);
}
