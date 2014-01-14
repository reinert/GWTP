package com.gwtplatform.mvp.databind.client.property;

import javax.annotation.Nullable;

/**
 * @author Danilo Reinert
 */
public interface ProvidesBoolean<T> extends ProvidesValue<T, Boolean> {

    public @Nullable Boolean getValue(T t);
}
