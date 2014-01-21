package com.gwtplatform.mvp.databind.client;


import com.google.web.bindery.event.shared.HandlerRegistration;
import com.gwtplatform.mvp.databind.client.validation.ValidationFailureHandler;
import com.gwtplatform.mvp.databind.client.validation.ValidationHandler;

/**
 * @author Danilo Reinert
 */
@Deprecated
public interface ValidationHandlerBinder extends Binder {

    HandlerRegistration bindValidationHandler(String id, ValidationHandler validationHandler);

    HandlerRegistration bindValidationHandler(String id, ValidationFailureHandler validationFailureHandler);
}
