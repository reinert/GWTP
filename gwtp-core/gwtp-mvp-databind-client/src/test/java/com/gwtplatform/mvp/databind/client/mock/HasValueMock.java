package com.gwtplatform.mvp.databind.client.mock;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;

/**
 * @author Danilo Reinert
 */
public class HasValueMock<V> extends TakesValueMock<V> implements HasValue<V> {

    private HandlerManager handlerManager;

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<V> handler) {
        return addHandler(handler, ValueChangeEvent.getType());
    }

    @Override
    public void fireEvent(GwtEvent<?> event) {
        if (handlerManager != null) {
            handlerManager.fireEvent(event);
        }
    }

    public final <H extends EventHandler> HandlerRegistration addHandler(H handler, GwtEvent.Type<H> type) {
        return ensureHandlerManager().addHandler(type, handler);
    }

    @Override
    public void setValue(V value) {
        setValue(value, true);
    }

    @Override
    public void setValue(V value, boolean fireEvents) {
        V oldValue = getValue();
        super.setValue(value);
        if (fireEvents) {
            ValueChangeEvent.fireIfNotEqual(this, oldValue, value);
        }
    }

    private HandlerManager ensureHandlerManager() {
        return handlerManager == null ? handlerManager = new HandlerManager(this) : handlerManager;
    }
}
