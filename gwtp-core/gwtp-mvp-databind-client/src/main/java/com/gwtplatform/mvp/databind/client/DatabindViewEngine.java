package com.gwtplatform.mvp.databind.client;


import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.HasValue;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.gwtplatform.mvp.client.HasUiHandlers;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Danilo Reinert
 */
@SuppressWarnings("unchecked")
public class DatabindViewEngine implements WidgetBinder, HasBindingValues, HasUiHandlers<DatabindUiHandlers>
        /* ValidationHandlerBinder, DatabindValidationHandler */ {

    private static class WidgetBinding {

        TakesValue widget;
        HandlerRegistration widgetHandlerRegistration;
        // TODO: Allow binding ValidationHandler?
        //ValidationHandler validationHandler;

        WidgetBinding(TakesValue widget, HandlerRegistration widgetHandlerRegistration) {
            this.widget = widget;
            this.widgetHandlerRegistration = widgetHandlerRegistration;
        }

        WidgetBinding(TakesValue widget) {
            this.widget = widget;
        }

        /*
        WidgetBinding(ValidationHandler validationHandler) {
            this.validationHandler = validationHandler;
        }
        */
    }

    //TODO: substitute map by a simple javascript object to increase performance
    private final Map<String, WidgetBinding> bindings = new HashMap<String, WidgetBinding>();
    private DatabindUiHandlers uiHandlers;

    /*
    @Override
    public void onValidationFailure(String id, ValidationMessage message) {
        WidgetBinding widgetBinding = bindings.get(id);
        if (widgetBinding != null && widgetBinding.validationHandler != null) {
            widgetBinding.validationHandler.onValidationFailure(message);
        }
    }

    @Override
    public void onValidationSuccess(String id, ValidationMessage message) {
        WidgetBinding widgetBinding = bindings.get(id);
        if (widgetBinding != null && widgetBinding.validationHandler != null) {
            widgetBinding.validationHandler.onValidationSuccess(message);
        }
    }

    @Override
    public HandlerRegistration bindValidationHandler(String id, ValidationHandler validationHandler) {
        WidgetBinding widgetBinding;
        if (bindings.containsKey(id)) {
            widgetBinding = bindings.get(id);
            widgetBinding.validationHandler = validationHandler;
        } else {
            widgetBinding = new WidgetBinding(validationHandler);
            bindings.put(id, widgetBinding);
        }
        return BinderHandlerRegistration.of(this, id);
    }
    */

    @Override
    public <F> F getValue(String id) {
        final WidgetBinding widgetBinding = bindings.get(id);
        if (widgetBinding != null) {
            TakesValue<?> hasValue = widgetBinding.widget;
            if (hasValue != null) {
                return (F) hasValue.getValue();
            }
        }
        return null;
    }

    @Override
    public <F> void setValue(String id, F value) {
        final WidgetBinding widgetBinding = bindings.get(id);
        if (widgetBinding != null) {
            TakesValue<?> hasValue = widgetBinding.widget;
            if (hasValue != null) ((TakesValue<F>) hasValue).setValue(value);
        }
    }

    @Override
    public void setUiHandlers(DatabindUiHandlers uiHandlers) {
        this.uiHandlers = uiHandlers;
    }

    @Override
    public <F> HandlerRegistration bind(String id, HasValue<F> widget, Strategy strategy) {
        //assert (widget instanceof IsWidget) : "HasValue parameter must be of type IsWidget";

        // Add update handler
        HandlerRegistration handlerRegistration = null;
        if (strategy == Strategy.ON_CHANGE) {
            handlerRegistration = addChangeHandlerToBoundWidget(id, widget);
        }

        if (bindings.containsKey(id)) {
            // If id were already bound, then update the binding
            WidgetBinding widgetBinding = bindings.get(id);
            if (widgetBinding.widgetHandlerRegistration != null) {
                // Remove previous existing handler avoiding memory leak
                widgetBinding.widgetHandlerRegistration.removeHandler();
            }
            widgetBinding.widget = widget;
            widgetBinding.widgetHandlerRegistration = handlerRegistration;
        } else {
            WidgetBinding widgetBinding = new WidgetBinding(widget, handlerRegistration);
            bindings.put(id, widgetBinding);
        }
        return BinderHandlerRegistration.of(this, id);
    }

    @Override
    public <F> HandlerRegistration bind(String id, TakesValue<F> widget) {
        //assert (widget instanceof IsWidget) : "TakesValue parameter must be of type IsWidget";

        if (bindings.containsKey(id)) {
            WidgetBinding widgetBinding = bindings.get(id);
            widgetBinding.widget = widget;
        } else {
            WidgetBinding widgetBinding = new WidgetBinding(widget);
            bindings.put(id, widgetBinding);
        }
        return BinderHandlerRegistration.of(this, id);
    }

    private <F> HandlerRegistration addChangeHandlerToBoundWidget(final String id, final HasValue<F> widget) {
        return widget.addValueChangeHandler(new ValueChangeHandler<F>() {
            @Override
            public void onValueChange(ValueChangeEvent<F> event) {
                // Avoid NPE. The null uiHandlers should be notified before reaching here.
                if (uiHandlers != null) {
                    uiHandlers.onValueChanged(id, event.getValue());
                }
            }
        });
    }

    @Override
    public boolean unbind(String id) {
        final WidgetBinding widgetBinding = bindings.remove(id);
        if (widgetBinding != null) {
            if (widgetBinding.widgetHandlerRegistration != null) {
                widgetBinding.widgetHandlerRegistration.removeHandler();
            }
            return true;
        }
        return false;
    }
}
