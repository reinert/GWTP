package com.gwtplatform.mvp.databind.client.validation;

/**
 * @author Danilo Reinert
 */
public interface InvalidValueHandler<T, F> {

    void onInvalidValue(String property, T object, F value, ValidationMessage message);
}
