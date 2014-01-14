package com.gwtplatform.mvp.databind.client;


import com.google.web.bindery.event.shared.HandlerRegistration;
import com.gwtplatform.mvp.databind.client.validation.InvalidValueHandler;
import com.gwtplatform.mvp.databind.client.validation.ValidationHandler;

/**
 * @author Danilo Reinert
 */
public interface ValidationHandlerBinder extends Binder {

    <T, F> HandlerRegistration bindValidationHandler(String id, ValidationHandler<T, F> validationHandler);

    <T, F> HandlerRegistration bindValidationHandler(String id, InvalidValueHandler<T, F> invalidValueHandler);
}
