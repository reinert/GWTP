package com.gwtplatform.mvp.databind.client;


import com.google.web.bindery.event.shared.HandlerRegistration;
import com.gwtplatform.mvp.databind.client.format.Formatter;
import com.gwtplatform.mvp.databind.client.format.ReadFormatter;
import com.gwtplatform.mvp.databind.client.property.PropertyAccessor;
import com.gwtplatform.mvp.databind.client.property.ProvidesValue;
import com.gwtplatform.mvp.databind.client.validation.Validation;
import com.gwtplatform.mvp.databind.client.validation.Validator;

import java.util.Iterator;

/**
 * @author Danilo Reinert
 */
public class Binding<T> implements PropertyBinder<T>, DatabindUiHandlers, Iterable<String> {

    private T model;
    private final DatabindView view;
    private PresenterEngine<T> engine = new PresenterEngine<T>();

    public Binding(DatabindView view) {
        this.view = view;
    }

    @Override
    public <F> HandlerRegistration bindProperty(boolean autoBind, String id, PropertyAccessor<T, F> propertyAccessor, Validator<T, F> validatesValue, Formatter<F, ?> formatter) {
        return engine.bindProperty(autoBind, id, propertyAccessor, validatesValue, formatter);
    }

    @Override
    public <F> HandlerRegistration bindProperty(boolean autoBind, String id, PropertyAccessor<T, F> propertyAccessor, Validator<T, F> validatesValue) {
        return engine.bindProperty(autoBind, id, propertyAccessor, validatesValue);
    }

    @Override
    public <F> HandlerRegistration bindProperty(boolean autoBind, String id, PropertyAccessor<T, F> propertyAccessor, Formatter<F, ?> formatter) {
        return engine.bindProperty(autoBind, id, propertyAccessor, formatter);
    }

    @Override
    public <F> HandlerRegistration bindProperty(boolean autoBind, String id, ProvidesValue<T, F> providesValue, ReadFormatter<F, ?> readFormatter) {
        return engine.bindProperty(autoBind, id, providesValue, readFormatter);
    }

    @Override
    public <F> HandlerRegistration bindProperty(String id, PropertyAccessor<T, F> propertyAccessor, Validator<T, F> validatesValue, Formatter<F, ?> formatter) {
        return engine.bindProperty(id, propertyAccessor, validatesValue, formatter);
    }

    public <F> HandlerRegistration bindProperty(String id, PropertyAccessor<T, F> propertyAccessor, Validator<T, F> validatesValue) {
        return engine.bindProperty(id, propertyAccessor, validatesValue);
    }

    @Override
    public <F> HandlerRegistration bindProperty(String id, PropertyAccessor<T, F> propertyAccessor, Formatter<F, ?> formatter) {
        return engine.bindProperty(id, propertyAccessor, formatter);
    }

    @Override
    public <F> HandlerRegistration bindProperty(String id, ProvidesValue<T, F> providesValue, ReadFormatter<F, ?> readFormatter) {
        return engine.bindProperty(id, providesValue, readFormatter);
    }

    public <F> HandlerRegistration bindProperty(String id, ProvidesValue<T, F> providesValue) {
        return engine.bindProperty(id, providesValue);
    }

    public <F> HandlerRegistration bindProperty(boolean autoBind, String id, ProvidesValue<T, F> providesValue) {
        return engine.bindProperty(autoBind, id, providesValue);
    }

    public <F> HandlerRegistration bindProperty(boolean autoBind, String id, PropertyAccessor<T, F> propertyAccessor) {
        return engine.bindProperty(autoBind, id, propertyAccessor);
    }

    public <F> HandlerRegistration bindProperty(String id, PropertyAccessor<T, F> propertyAccessor) {
        return engine.bindProperty(id, propertyAccessor);
    }

    public Validation isValueValid(String id, T model, Object value) {
        return engine.isValueValid(id, model, value);
    }

    public Object getValue(String id, T model) {
        return engine.getValue(id, model);
    }

    public void setValue(String id, T model, Object value) {
        engine.setValue(id, model, value);
    }

    public PropertyAccessor<T, ?> getPropertyAccessor(String id) {
        return engine.getPropertyAccessor(id);
    }

    public Validator<T, ?> getValidatesValue(String id) {
        return engine.getValidatesValue(id);
    }

    public boolean isAutoBind(String id) {
        return engine.isAutoBind(id);
    }

    public boolean hasProperty(String id) {
        return engine.hasProperty(id);
    }

    @Override
    public Iterator<String> iterator() {
        return engine.iterator();
    }

    public boolean flush() {
        boolean isValid = true;
        for (String id : engine) {
            Object value = view.getValue(id);
            // MUST PASS THROUGH VALIDATION ALWAYS
            if (!onValueChanged(id, value))
                isValid = false;
        }
        return isValid;
    }

    public boolean flush(String id) {
        boolean isValid = true;
        if (engine.hasProperty(id)) {
            Object value = view.getValue(id);
            if (value != null && !value.equals(engine.getValue(id, model)))
                if (!onValueChanged(id, value))
                    isValid = false;
        }
        return isValid;
    }

    public void refresh() {
        for (String id : engine) {
            if (engine.isAutoBind(id)) {
                setValueToView(id);
            }
        }
    }

    public void forceRefresh(String id) {
        if (engine.hasProperty(id)) {
            setValueToView(id);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean onValueChanged(String id, Object value) {
        boolean isValid = true;
        final Validation validation = engine.isValueValid(id, model, value);
        if (validation.isValid()) {
            engine.setValue(id, model, value);
            if (model != null) view.onValidValue(id, model, value, validation.getValidationMessage());
        } else {
            isValid = false;
            view.onInvalidValue(id, model, value, validation.getValidationMessage());
        }
        return isValid;
    }

    public void setModel(T model) {
        this.model = model;
        refresh();
    }

    public T getModel() {
        return model;
    }

    public DatabindView getView() {
        return view;
    }

    private void setValueToView(String id) {
        if (model != null) {
            view.setValue(id, engine.getValue(id, model));
        } else {
            view.setValue(id, null);
        }
    }

    @Override
    public boolean unbind(String id) {
        return engine.unbind(id);
    }
}
