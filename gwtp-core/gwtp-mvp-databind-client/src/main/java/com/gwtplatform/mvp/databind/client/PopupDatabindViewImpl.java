package com.gwtplatform.mvp.databind.client;

import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.HasValue;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;
import com.gwtplatform.mvp.databind.client.validation.ValidationMessage;

/**
 * @author Danilo Reinert
 */
//TODO: docs
public class PopupDatabindViewImpl<H extends DatabindUiHandlers> extends PopupViewWithUiHandlers<H>
        implements PopupDatabindView<H> {

    private final DatabindViewEngine engine = new DatabindViewEngine();

    /**
     * The {@link com.gwtplatform.mvp.client.PopupViewWithUiHandlers} class uses the {@link com.google.web.bindery
     * .event.shared.EventBus} to listen to
     * {@link com.gwtplatform.mvp.client.proxy.NavigationEvent} in order to automatically
     * close when this event is fired, if desired. See
     * {@link #setAutoHideOnNavigationEventEnabled(boolean)} for details.
     *
     * @param eventBus The {@link com.google.web.bindery.event.shared.EventBus}.
     */
    protected PopupDatabindViewImpl(EventBus eventBus) {
        super(eventBus);
    }

    @Override
    public <F> HandlerRegistration bind(String id, HasValue<F> widget, Strategy strategy) {
        return engine.bind(id, widget, strategy);
    }

    @Override
    public <F> HandlerRegistration bind(String id, TakesValue<F> widget) {
        return engine.bind(id, widget);
    }

    /*
    @Override
    public <T, F> HandlerRegistration bindValidationHandler(String id, InvalidValueHandler<T, F> invalidValueHandler) {
        return engine.bindValidationHandler(id, invalidValueHandler);
    }

    @Override
    public <T, F> HandlerRegistration bindValidationHandler(String id, ValidationHandler<T, F> validationHandler) {
        return engine.bindValidationHandler(id, validationHandler);
    }
    */

    @Override
    public <F> F getValue(String id) {
        return engine.getValue(id);
    }

    @Override
    public <T, F> void onInvalidValue(String id, T object, F value, ValidationMessage message) {
        //engine.onInvalidValue(id, object, value, message);
    }

    @Override
    public <T, F> void onValidValue(String id, T object, F value, ValidationMessage message) {
        //engine.onValidValue(id, object, value, message);
    }

    @Override
    public void setUiHandlers(H uiHandlers) {
        super.setUiHandlers(uiHandlers);
        engine.setUiHandlers(uiHandlers);
    }

    @Override
    public <F> void setValue(String id, F value) {
        engine.setValue(id, value);
    }

    @Override
    public boolean unbind(String id) {
        return engine.unbind(id);
    }
}
