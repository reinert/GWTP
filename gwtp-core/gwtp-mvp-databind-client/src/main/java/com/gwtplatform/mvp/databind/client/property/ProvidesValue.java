package com.gwtplatform.mvp.databind.client.property;

import javax.annotation.Nullable;

/**
 * A value provider of an object, usually calling some getter.
 *
 * @author Danilo Reinert
 */
public interface ProvidesValue<T, F> {

    /**
     * Given a object, returns the value of a property.
     * The property is known at compile time.
     *
     * @param t object to be accessed
     * @return value of a property of the object
     */
    public @Nullable F getValue(T t);
}
