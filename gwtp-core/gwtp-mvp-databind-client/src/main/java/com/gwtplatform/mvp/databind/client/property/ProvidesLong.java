package com.gwtplatform.mvp.databind.client.property;

import javax.annotation.Nullable;

/**
 * @author Danilo Reinert
 */
public interface ProvidesLong<T> extends ProvidesNumber<T> {

    public @Nullable Long getValue(T t);
}
