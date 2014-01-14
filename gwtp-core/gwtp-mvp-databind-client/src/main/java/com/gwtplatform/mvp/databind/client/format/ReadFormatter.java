package com.gwtplatform.mvp.databind.client.format;

import javax.annotation.Nullable;

/**
 * @author Danilo Reinert
 */
public interface ReadFormatter<MODEL, VIEW> {

    @Nullable
    VIEW format(@Nullable MODEL value);
}
