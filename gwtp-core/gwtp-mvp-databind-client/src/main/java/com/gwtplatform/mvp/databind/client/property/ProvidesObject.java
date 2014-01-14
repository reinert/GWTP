package com.gwtplatform.mvp.databind.client.property;

import javax.annotation.Nullable;

/**
 * @author Danilo Reinert
 */
public interface ProvidesObject<T> extends ProvidesValue<T, Object> {

    public @Nullable Object getValue(T t);
}
