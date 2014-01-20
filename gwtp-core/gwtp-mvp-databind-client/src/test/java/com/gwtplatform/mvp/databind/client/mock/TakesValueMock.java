package com.gwtplatform.mvp.databind.client.mock;

import com.google.gwt.user.client.TakesValue;

/**
 * @author Danilo Reinert
 */
public class TakesValueMock<V> implements TakesValue<V> {

    private V value;

    @Override
    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public V getValue() {
        return this.value;
    }
}
