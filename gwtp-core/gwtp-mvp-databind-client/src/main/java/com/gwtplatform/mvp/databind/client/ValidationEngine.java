package com.gwtplatform.mvp.databind.client;


import com.google.web.bindery.event.shared.HandlerRegistration;
import com.gwtplatform.mvp.databind.client.validation.InvalidValueHandler;
import com.gwtplatform.mvp.databind.client.validation.ValidationHandler;
import com.gwtplatform.mvp.databind.client.validation.ValidationMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Danilo Reinert
 */
public class ValidationEngine implements ValidationHandlerBinder, DatabindValidationHandler {

    private Map<String, ValidationHandler> validationHandlerMap;

    public HandlerRegistration bindValidationHandler(String id, ValidationHandler validationHandler) {
        ensureMap().put(id, validationHandler);
        return BinderHandlerRegistration.of(this, id);
    }

    public <T, F> HandlerRegistration bindValidationHandler(String id,
                                                            final InvalidValueHandler<T, F> invalidValueHandler) {
        ensureMap().put(id, new ValidationHandler<T, F>() {
            @Override
            public void onValidValue(T object, F value, ValidationMessage message) {
            }

            @Override
            public void onInvalidValue(T object, F value, ValidationMessage message) {
                invalidValueHandler.onInvalidValue(object, value, message);
            }
        });
        return BinderHandlerRegistration.of(this, id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T, F> void onInvalidValue(String property, T object, F value, ValidationMessage message) {
        if (validationHandlerMap != null) {
            ValidationHandler databindValidationHandler = validationHandlerMap.get(property);
            if (databindValidationHandler != null) {
                databindValidationHandler.onInvalidValue(object, value, message);
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T, F> void onValidValue(String property, T object, F value, ValidationMessage message) {
        if (validationHandlerMap != null) {
            ValidationHandler databindValidationHandler = validationHandlerMap.get(property);
            if (databindValidationHandler != null) {
                databindValidationHandler.onValidValue(object, value, message);
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
