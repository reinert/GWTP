package com.gwtplatform.mvp.databind.client.property;

import javax.annotation.Nullable;

/**
 * @author Danilo Reinert
 */
public interface ProvidesNumber<T> extends ProvidesValue<T, Number> {

    public @Nullable Number getValue(T t);
}
