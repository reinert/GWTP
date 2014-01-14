package com.gwtplatform.mvp.databind.client.format;

import javax.annotation.Nullable;

/**
 * @author Danilo Reinert
 */
public interface Formatter<MODEL, VIEW> extends ReadFormatter<MODEL, VIEW> {

    @Nullable
    MODEL unformat(@Nullable VIEW value);
}
