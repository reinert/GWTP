package com.gwtplatform.mvp.databind.client.mock;

import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.gwtplatform.mvp.databind.client.DatabindUiHandlers;
import com.gwtplatform.mvp.databind.client.DatabindView;
import com.gwtplatform.mvp.databind.client.DatabindViewEngine;
import com.gwtplatform.mvp.databind.client.validation.InvalidValueHandler;
import com.gwtplatform.mvp.databind.client.validation.ValidationHandler;
import com.gwtplatform.mvp.databind.client.validation.ValidationMessage;

/**
 * @author Danilo Reinert
 */
public class DatabindViewMock<H extends DatabindUiHandlers> implements DatabindView<H> {

    private final DatabindViewEngine engine = new DatabindViewEngine();

    @Override
    public void addToSlot(Object slot, IsWidget content) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public Widget asWidget() {
        return null;
    }

    @Override
    public <F> HandlerRegistration bindReadOnlyWidget(String id, TakesValue<F> widget) {
        return engine.bindReadOnlyWidget(id, widget);
    }

    @Override
    public <T, F> HandlerRegistration bindValidationHandler(String id, ValidationHandler<T, F> validationHandler) {
        return engine.bindValidationHandler(id, validationHandler);
    }

    @Override
    public <T, F> HandlerRegistration bindValidationHandler(String id, InvalidValueHandler<T, F> invalidValueHandler) {
        return engine.bindValidationHandler(id, invalidValueHandler);
    }

    @Override
    public <F> HandlerRegistration bindWidget(String id, HasValue<F> widget) {
        return engine.bindWidget(id, widget);
    }

    @Override
    public <F> F getValue(String id) {
        return engine.getValue(id);
    }

    @Override
    public <T, F> void onInvalidValue(String id, T object, F value, ValidationMessage message) {
        engine.onInvalidValue(id, object, value, message);
    }

    @Override
    public <T, F> void onValidValue(String id, T object, F value, ValidationMessage message) {
        engine.onValidValue(id, object, value, message);
    }

    @Override
    public void removeFromSlot(Object slot, IsWidget content) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void setInSlot(Object slot, IsWidget content) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void setUiHandlers(DatabindUiHandlers uiHandlers) {
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
