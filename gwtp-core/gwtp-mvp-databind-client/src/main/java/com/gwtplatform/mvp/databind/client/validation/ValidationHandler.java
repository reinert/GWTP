package com.gwtplatform.mvp.databind.client.validation;

/**
 * @author Danilo Reinert
 */
public interface ValidationHandler<T, F> extends InvalidValueHandler<T, F> {

    void onValidValue(String property, T object, F value, ValidationMessage message);
}
