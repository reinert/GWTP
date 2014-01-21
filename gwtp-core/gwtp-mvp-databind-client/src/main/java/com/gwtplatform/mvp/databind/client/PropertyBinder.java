package com.gwtplatform.mvp.databind.client;


import com.google.web.bindery.event.shared.HandlerRegistration;
import com.gwtplatform.mvp.databind.client.format.Formatter;
import com.gwtplatform.mvp.databind.client.property.PropertyAccessor;
import com.gwtplatform.mvp.databind.client.validation.Validator;

/**
 * A Binder that supports binding property accessors, validators and formatters to a property id.
 *
 * @author Danilo Reinert
 */
public interface PropertyBinder<T> extends Binder {

//    <F> HandlerRegistration bind(String id, ProvidesValue<T, F> providesValue);
//
//    <F> HandlerRegistration bind(String id, ProvidesValue<T, F> providesValue,
//                                         ReadFormatter<F, ?> readFormatter);

    <F> HandlerRegistration bind(String id, PropertyAccessor<T, F> propertyAccessor);

    <F> HandlerRegistration bind(String id, PropertyAccessor<T, F> propertyAccessor,
                                 Validator<T, F> validatesValue);

    <F> HandlerRegistration bind(String id, PropertyAccessor<T, F> propertyAccessor,
                                 Validator<T, F> validatesValue, Formatter<F, ?> formatter);

    <F> HandlerRegistration bind(String id, PropertyAccessor<T, F> propertyAccessor, Formatter<F, ?> formatter);

//    <F> HandlerRegistration bind(boolean autoRefresh, String id, ProvidesValue<T, F> providesValue);
//
//    <F> HandlerRegistration bind(boolean autoRefresh, String id, ProvidesValue<T, F> providesValue,
//                                         ReadFormatter<F, ?> readFormatter);

    <F> HandlerRegistration bind(boolean autoRefresh, String id, PropertyAccessor<T, F> propertyAccessor);

    <F> HandlerRegistration bind(boolean autoRefresh, String id, PropertyAccessor<T, F> propertyAccessor,
                                 Validator<T, F> validatesValue);

    <F> HandlerRegistration bind(boolean autoRefresh, String id, PropertyAccessor<T, F> propertyAccessor,
                                 Validator<T, F> validatesValue, Formatter<F, ?> formatter);

    <F> HandlerRegistration bind(boolean autoRefresh, String id, PropertyAccessor<T, F> propertyAccessor,
                                 Formatter<F, ?> formatter);
}
