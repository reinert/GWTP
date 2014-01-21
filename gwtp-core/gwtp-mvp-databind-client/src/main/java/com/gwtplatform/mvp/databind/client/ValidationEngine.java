package com.gwtplatform.mvp.databind.client;


import com.google.web.bindery.event.shared.HandlerRegistration;
import com.gwtplatform.mvp.databind.client.validation.ValidationFailureHandler;
import com.gwtplatform.mvp.databind.client.validation.ValidationHandler;
import com.gwtplatform.mvp.databind.client.validation.ValidationMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Danilo Reinert
 */
@Deprecated
public class ValidationEngine implements ValidationHandlerBinder, DatabindValidationHandler {

    //TODO: substitute map by a simple javascript object to increase performance
    private Map<String, ValidationHandler> validationHandlerMap;

    public HandlerRegistration bindValidationHandler(String id, ValidationHandler validationHandler) {
        ensureMap().put(id, validationHandler);
        return BinderHandlerRegistration.of(this, id);
    }

    public HandlerRegistration bindValidationHandler(String id, final ValidationFailureHandler validationFailureHandler) {
        ensureMap().put(id, new ValidationHandler() {
            @Override
            public void onValidationSuccess(ValidationMessage message) {
            }

            @Override
            public void onValidationFailure(ValidationMessage message) {
                validationFailureHandler.onValidationFailure(message);
            }
        });
        return BinderHandlerRegistration.of(this, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onValidationFailure(String property, ValidationMessage message) {
        if (validationHandlerMap != null) {
            ValidationHandler databindValidationHandler = validationHandlerMap.get(property);
            if (databindValidationHandler != null) {
                databindValidationHandler.onValidationFailure(message);
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onValidationSuccess(String property, ValidationMessage message) {
        if (validationHandlerMap != null) {
            ValidationHandler databindValidationHandler = validationHandlerMap.get(property);
            if (databindValidationHandler != null) {
                databindValidationHandler.onValidationSuccess(message);
            }
        }
    }

    @Override
    public boolean unbind(String id) {
        if (validationHandlerMap == null) return false;
        final boolean result = validationHandlerMap.remove(id) != null;
        if (validationHandlerMap.isEmpty()) validationHandlerMap = null;
        return result;
    }

    private Map<String, ValidationHandler> ensureMap() {
        return validationHandlerMap = validationHandlerMap != null ?
                validationHandlerMap : new HashMap<String, ValidationHandler>();
    }
}
