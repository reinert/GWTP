package com.gwtplatform.mvp.databind.client.property;

import javax.annotation.Nullable;

/**
 * This interface is meant to provide access to some property of an object.
 * Simply, it should call the getter and setter of a specific property.
 *
 * Serves as support for third-party subjects to manage object properties,
 * following the IoC pattern.
 *
 * @see <a href="http://martinfowler.com/bliki/InversionOfControl.html">InversionOfControl</a>
 *
 * @author Danilo Reinert
 */
public interface PropertyAccessor<T, F> extends ProvidesValue<T, F> {

    /**
     * Sets the given value to the object.
     * The property is known at compile time.
     *
     * @param t the object containing the property
     * @param value the value to be set into object's property
     */
    void setValue(T t, @Nullable F value);
}
