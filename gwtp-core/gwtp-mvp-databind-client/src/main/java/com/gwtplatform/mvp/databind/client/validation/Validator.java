package com.gwtplatform.mvp.databind.client.validation;

import javax.annotation.Nullable;

/**
 * @author Danilo Reinert
 */
public interface Validator<T, F> {

    Validation validate(T object, @Nullable F value);
}
