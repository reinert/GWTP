package com.gwtplatform.mvp.databind.client.property;

import javax.annotation.Nullable;

/**
 * @author Danilo Reinert
 */
public interface ProvidesInt<T> extends ProvidesNumber<T> {

    public @Nullable Integer getValue(T t);
}
