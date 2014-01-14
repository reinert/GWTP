package com.gwtplatform.mvp.databind.client;

import com.gwtplatform.mvp.databind.client.validation.ValidationMessage;

/**
 * @author Danilo Reinert
 */
public interface DatabindValidationHandler extends DatabindInvalidValueHandler {

    <T, F> void onValidValue(String property, T object, F value, ValidationMessage message);
}
