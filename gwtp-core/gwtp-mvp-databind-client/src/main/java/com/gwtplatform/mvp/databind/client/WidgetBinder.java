package com.gwtplatform.mvp.databind.client;

import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.HasValue;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * @author Danilo Reinert
 */
public interface WidgetBinder extends Binder {

    <F> HandlerRegistration bindReadOnlyWidget(final String id, final TakesValue<F> widget);

    <F> HandlerRegistration bindWidget(final String id, final HasValue<F> widget);
}
