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
        return engine.getRawValue(id, model);
    }

    public void setValue(String id, T model, Object value) {
        engine.setRawValue(id, model, value);
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

    /**
     * Get all values from view, apply to the model and return if all of them was valid.
     *
     * @return {@code true} if all values were valid, {@code false} otherwise
     */
    public boolean flush() {
        boolean isValid = true;
        for (String id : engine) {
            isValid = doFlush(id);
        }
        return isValid;
    }

    /**
     * Get value from view, apply to the model and return if it was not invalid.
     *
     * @param id identification of the property
     * @return {@code false} if it was invalid, {@code true} otherwise
     */
    public boolean flush(String id) {
        if (engine.hasProperty(id)) {
            return doFlush(id);
        } // Id not bound, then the value was not invalid
        return true;
    }

    public void refresh() {
        for (String id : engine) {
            setValueToView(id);
        }
    }

    public void refresh(String id) {
        if (engine.hasProperty(id)) {
            setValueToView(id);
        }
    }

    @Override
    public void onValueChanged(String id, Object value) {
        setValueToModel(id, value);
    }

    /**
     * Validate and apply a value to a property and return if it was successfully set.
     * When the model is *null*, no value is set and {@code false} is returned.
     *
     * @param id identification of the property
     * @param value value to be applied
     * @return {@code true} if the value was validated and applied, {@code false} otherwise
     */
    @SuppressWarnings("unchecked")
    private boolean setValueToModel(String id, Object value) {
        if (model == null) return false;

        // First, validate the value
        final Validation validation = engine.isValueValid(id, model, value);
        if (validation.isValid()) {
            // If valid, then set to the model and fire valid value event
            engine.setFormattedValue(id, model, value);
            view.onValidValue(id, model, value, validation.getValidationMessage());
            return true;
        } else {
            // It must be executed only when a validation occurs and it returns invalid
            view.onInvalidValue(id, model, value, validation.getValidationMessage());
        }

        return false;
    }

    public T getModel() {
        return model;
    }

    public DatabindView getView() {
        return view;
    }

    public void setModel(T model) {
        this.model = model;
        refresh();
    }

    @Override
    public boolean unbind(String id) {
        return engine.unbind(id);
    }

    private boolean doFlush(String id) {
        Object formattedValue = view.getValue(id);
        if (formattedValue != null && engine.isValueDifferent(id, formattedValue)) {
            // Value differs from current
            if (!setValueToModel(id, formattedValue)) {
                // Could not set a different value to model, then it was invalid
                return false;
            }
        }
        return true;
    }

    private void refreshAuto() {
        for (String id : engine) {
            if (engine.isAutoBind(id)) {
                setValueToView(id);
            }
        }
    }

    private void setValueToView(String id) {
        if (model != null) {
            view.setValue(id, engine.getFormattedValue(id, model));
        } else {
            // TODO: is this really necessary?
            view.setValue(id, null);
        }
    }
}
