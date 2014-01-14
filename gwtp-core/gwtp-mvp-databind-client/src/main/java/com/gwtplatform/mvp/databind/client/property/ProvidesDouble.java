package com.gwtplatform.mvp.databind.client.property;

import javax.annotation.Nullable;

/**
 * @author Danilo Reinert
 */
public interface ProvidesDouble<T> extends ProvidesNumber<T> {

    public @Nullable Double getValue(T t);
}
