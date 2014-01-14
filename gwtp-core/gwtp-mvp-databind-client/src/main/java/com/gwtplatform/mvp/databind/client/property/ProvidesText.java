package com.gwtplatform.mvp.databind.client.property;

import javax.annotation.Nullable;

/**
 * @author Danilo Reinert
 */
public interface ProvidesText<T> extends ProvidesValue<T, String> {

    public @Nullable String getValue(T t);
}
