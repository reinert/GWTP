package com.gwtplatform.mvp.databind.client.format;

import javax.annotation.Nullable;

/**
 * Two-way formatter used for formatting and parsing values to/from view.
 *
 * @author Danilo Reinert
 */
public interface Formatter<MODEL, VIEW> extends ReadFormatter<MODEL, VIEW> {

    /**
     * Given the formatted value, it parses into a row value for sending to the model.
     * The Model should know only about the raw value.
     *
     * @param formattedValue held by the view
     * @return raw value
     */
    @Nullable
    MODEL unformat(@Nullable VIEW formattedValue);
}
