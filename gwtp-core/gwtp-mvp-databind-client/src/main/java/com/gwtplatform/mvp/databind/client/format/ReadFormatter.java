package com.gwtplatform.mvp.databind.client.format;

import javax.annotation.Nullable;

/**
 * One-way formatter for read-only circumstances.
 * Usually it will format a raw value to nicely present to the user.
 *
 * @author Danilo Reinert
 */
public interface ReadFormatter<MODEL, VIEW> {

    /**
     * Given the raw value, it returns a formatted value for view presentation.
     * The View should know only about the formatted value.
     *
     * @param rawValue raw value held by the model
     * @return formatted value
     */
    @Nullable
    VIEW format(@Nullable MODEL rawValue);
}
