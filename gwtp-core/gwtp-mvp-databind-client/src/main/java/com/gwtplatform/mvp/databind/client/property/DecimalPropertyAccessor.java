package com.gwtplatform.mvp.databind.client.property;

import java.math.BigDecimal;

/**
 * @author Danilo Reinert
 */
public interface DecimalPropertyAccessor<T> extends PropertyAccessor<T, BigDecimal>, ProvidesDecimal<T> {
}
