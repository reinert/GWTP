package com.gwtplatform.mvp.databind.client.property;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * @author Danilo Reinert
 */
public interface ProvidesDecimal<T> extends ProvidesValue<T, BigDecimal> {

    public @Nullable BigDecimal getValue(T t);
}
