package com.gwtplatform.mvp.databind.client;


import com.google.web.bindery.event.shared.HandlerRegistration;
import com.gwtplatform.mvp.databind.client.format.Formatter;
import com.gwtplatform.mvp.databind.client.format.ReadFormatter;
import com.gwtplatform.mvp.databind.client.property.PropertyAccessor;
import com.gwtplatform.mvp.databind.client.property.ProvidesValue;
import com.gwtplatform.mvp.databind.client.validation.Validator;

/**
 * @author Danilo Reinert
 */
public interface PropertyBinder<T> extends Binder {

    <F> HandlerRegistration bindProperty(String id, PropertyAccessor<T, F> propertyAccessor);

    <F> HandlerRegistration bindProperty(String id, ProvidesValue<T, F> providesValue);

    <F> HandlerRegistration bindProperty(String id, ProvidesValue<T, F> providesValue, ReadFormatter<F, ?> readFormatter);

    <F> HandlerRegistration bindProperty(String id, PropertyAccessor<T, F> propertyAccessor, Formatter<F, ?> formatter);

    <F> HandlerRegistration bindProperty(String id, PropertyAccessor<T, F> propertyAccessor, Validator<T, F> validatesValue);

    <F> HandlerRegistration bindProperty(String id, PropertyAccessor<T, F> propertyAccessor, Validator<T, F> validatesValue,
                          Formatter<F, ?> formatter);

    <F> HandlerRegistration bindProperty(boolean autoBind, String id, PropertyAccessor<T, F> propertyAccessor);

    <F> HandlerRegistration bindProperty(boolean autoBind, String id, ProvidesValue<T, F> providesValue);

    <F> HandlerRegistration bindProperty(boolean autoBind, String id, ProvidesValue<T, F> providesValue,
                          ReadFormatter<F, ?> readFormatter);

    <F> HandlerRegistration bindProperty(boolean autoBind, String id, PropertyAccessor<T, F> propertyAccessor,
                          Formatter<F, ?> formatter);

    <F> HandlerRegistration bindProperty(boolean autoBind, String id, PropertyAccessor<T, F> propertyAccessor,
                          Validator<T, F> validatesValue);

    <F> HandlerRegistration bindProperty(boolean autoBind, String id, PropertyAccessor<T, F> propertyAccessor,
                          Validator<T, F> validatesValue, Formatter<F, ?> formatter);

}