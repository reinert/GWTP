package com.gwtplatform.mvp.databind.client;

import com.gwtplatform.mvp.databind.client.validation.ValidationMessage;

/**
 * @author Danilo Reinert
 */
public interface DatabindInvalidValueHandler {

    <T, F> void onInvalidValue(String property, T object, F value, ValidationMessage message);
}
