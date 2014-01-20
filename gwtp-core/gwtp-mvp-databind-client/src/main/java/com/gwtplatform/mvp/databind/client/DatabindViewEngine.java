package com.gwtplatform.mvp.databind.client;


import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.HasValue;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.databind.client.validation.InvalidValueHandler;
import com.gwtplatform.mvp.databind.client.validation.ValidationHandler;
import com.gwtplatform.mvp.databind.client.validation.ValidationMessage;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Danilo Reinert
 */
@SuppressWarnings("unchecked")
public class DatabindViewEngine implements ValidationHandlerBinder, DatabindValidationHandler,
        WidgetBinder, HasBindingValues, HasUiHandlers<DatabindUiHandlers> {

    private static class Holder {

        TakesValue widget;
        ValidationHandler validationHandler;
        HandlerRegistration widgetHandlerRegistration;

        Holder(TakesValue widget, HandlerRegistration widgetHandlerRegistration) {
            this.widget = widget;
            this.widgetHandlerRegistration = widgetHandlerRegistration;
        }

        Holder(TakesValue widget) {
            this.widget = widget;
        }

        Holder(ValidationHandler validationHandler) {
            this.validationHandler = validationHandler;
        }
    }

    //TODO: substitute map by a simple javascript object to increase performance
    private final Map<String, Holder> holderMap = new LinkedHashMap<String, Holder>();
    private DatabindUiHandlers uiHandlers;

    @Override
    public <T, F> void onInvalidValue(String id, T object, F value, ValidationMessage message) {
        Holder holder = holderMap.get(id);
        if (holder != null && holder.validationHandler != null) {
            holder.validationHandler.onInvalidValue(object, value, message);
        }
    }

    @Override
    public <T, F> void onValidValue(String id, T object, F value, ValidationMessage message) {
        Holder holder = holderMap.get(id);
        if (holder != null && holder.validationHandler != null) {
            holder.validationHandler.onValidValue(object, value, message);
        }
    }

    @Override
    public <T, F> HandlerRegistration bindValidationHandler(String id, ValidationHandler<T, F> validationHandler) {
        Holder holder;
        if (holderMap.containsKey(id)) {
            holder = holderMap.get(id);
            holder.validationHandler = validationHandler;
        } else {
            holder = new Holder(validationHandler);
            holderMap.put(id, holder);
        }
        return BinderHandlerRegistration.of(this, id);
    }

    @Override
    public <T, F> HandlerRegistration bindValidationHandler(String id,
                                                         final InvalidValueHandler<T, F> invalidValueHandler) {
        return bindValidationHandler(id, new ValidationHandler<T, F>() {
            @Override
            public void onValidValue(T object, F value, ValidationMessage message) {}

            @Override
            public void onInvalidValue(T object, F value, ValidationMessage message) {
                invalidValueHandler.onInvalidValue(object, value, message);
            }
        });
    }

    @Override
    public <F> F getValue(String id) {
        final Holder holder = holderMap.get(id);
        if (holder != null) {
            TakesValue<?> hasValue = holder.widget;
            if (hasValue != null) {
                return (F) hasValue.getValue();
            }
        }
        return null;
    }

    @Override
    public <F> void setValue(String id, F value) {
        final Holder holder = holderMap.get(id);
        if (holder != null) {
            TakesValue<?> hasValue = holder.widget;
            if (hasValue != null) ((TakesValue<F>) hasValue).setValue(value);
        }
    }

    @Override
    public void setUiHandlers(DatabindUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    @Override
    public <F> HandlerRegistration bindWidget(final String id, final HasValue<F> widget) {
        //assert (widget instanceof IsWidget) : "HasValue parameter must be of type IsWidget";

        HandlerRegistration handlerRegistration = addChangeHandlerToBoundWidget(id, widget);
        if (holderMap.containsKey(id)) {
            Holder holder = holderMap.get(id);
            holder.widget = widget;
            if (holder.widgetHandlerRegistration != null) {
                // Remove previous existing handler avoiding memory leak
                holder.widgetHandlerRegistration.removeHandler();
            }
            holder.widgetHandlerRegistration = handlerRegistration;
        } else {
            Holder holder = new Holder(widget, handlerRegistration);
            holderMap.put(id, holder);
        }
        return BinderHandlerRegistration.of(this, id);
    }

    @Override
    public <F> HandlerRegistration bindReadOnlyWidget(final String id, final TakesValue<F> widget) {
        //assert (widget instanceof IsWidget) : "TakesValue parameter must be of type IsWidget";

        if (holderMap.containsKey(id)) {
            Holder holder = holderMap.get(id);
            holder.widget = widget;
        } else {
            Holder holder = new Holder(widget);
            holderMap.put(id, holder);
        }
        return BinderHandlerRegistration.of(this, id);
    }

    //TODO: Add commit strategies, onChange, onKey(Up), etc.
    private <F> HandlerRegistration addChangeHandlerToBoundWidget(final String id, final HasValue<F> widget) {
        return widget.addValueChangeHandler(new ValueChangeHandler<F>() {
            @Override
            public void onValueChange(ValueChangeEvent<F> event) {
                // Avoid NPE. The null uiHandlers should be notified before reach here.
                if (uiHandlers != null) {
                    uiHandlers.onValueChanged(id, event.getValue());
                }
            }
        });
    }

    @Override
    public boolean unbind(String id) {
        final Holder holder = holderMap.remove(id);
        if (holder != null) {
            if (holder.widgetHandlerRegistration != null) {
                holder.widgetHandlerRegistration.removeHandler();
            }
            return true;
        }
        return false;
    }
}
