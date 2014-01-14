package com.gwtplatform.mvp.databind.client;


import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.HandlerRegistration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Danilo Reinert
 */
public class WidgetEngine implements WidgetBinder {

    private DatabindUiHandlers uiHandlers;
    private Map<String, TakesValue> widgetMap;

    public WidgetEngine(DatabindUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    public <F> HandlerRegistration bindWidget(final String id, final HasValue<F> widget) {
        assert (widget instanceof Widget) : "HasValue parameter must be of type Widget";
        Widget w = (Widget) widget;
        w.getElement().setId(id);
        //TODO: Deal possible memory leak by binding the same id more than once
        // and losing previous HandlerRegistrations references
        HandlerRegistration handlerRegistration = addChangeHandlerToBoundWidget(id, widget);
        ensureMap().put(id, widget);
        return BinderHandlerRegistration.of(this, id, handlerRegistration);
    }

    public <F> HandlerRegistration bindReadOnlyWidget(final String id, final TakesValue<F> widget) {
        assert (widget instanceof Widget) : "TakesValue parameter must be of type Widget";
        ensureMap().put(id, widget);
        return BinderHandlerRegistration.of(this, id);
    }

    @Override
    public boolean unbind(String id) {
        if (widgetMap == null) return false;
        final boolean result = widgetMap.remove(id) != null;
        if (widgetMap.isEmpty()) widgetMap = null;
        return result;
    }

    //TODO: Add commit strategies, onChange, onKey(Up), etc.
    private <F> HandlerRegistration addChangeHandlerToBoundWidget(final String id, final HasValue<F> widget) {
        return widget.addValueChangeHandler(new ValueChangeHandler<F>() {
            @Override
            public void onValueChange(ValueChangeEvent<F> event) {
                uiHandlers.onValueChanged(id, widget.getValue());
            }
        });
    }

    private Map<String, TakesValue> ensureMap() {
        return widgetMap = widgetMap != null ?
                widgetMap : new HashMap<String, TakesValue>();
    }
}
