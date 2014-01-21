package com.gwtplatform.mvp.databind.client;

import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.HasValue;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * @author Danilo Reinert
 */
public interface WidgetBinder extends Binder {

    /**
     * Bind a widget to a property id.
     * Every time an event related to the selected strategy occurs, the updated value is sent to the
     * uiHandlers (Presenter)
     *
     * @param id
     * @param widget
     * @param strategy
     * @param <F>
     * @return
     */
    <F> HandlerRegistration bind(String id, HasValue<F> widget, Strategy strategy);

    /**
     * Bind a widget with default strategy (NONE).
     * This binding will behavior like a read-only widget.
     *
     * @param id identification of property
     * @param widget widget to bind
     * @param <F> value type
     * @return HandlerRegistration of this binding
     */
    <F> HandlerRegistration bind(String id, TakesValue<F> widget);
}
