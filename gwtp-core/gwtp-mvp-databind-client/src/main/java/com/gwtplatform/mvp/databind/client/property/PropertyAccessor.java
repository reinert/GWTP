package com.gwtplatform.mvp.databind.client.property;

import javax.annotation.Nullable;

/**
 * @author Danilo Reinert
 */
public interface PropertyAccessor<T, F> extends ProvidesValue<T, F> {

    void setValue(T t, @Nullable F value);
}
