package com.gwtplatform.mvp.databind.client;


import com.google.web.bindery.event.shared.HandlerRegistration;
import com.gwtplatform.mvp.databind.client.format.Formatter;
import com.gwtplatform.mvp.databind.client.property.PropertyAccessor;
import com.gwtplatform.mvp.databind.client.validation.Validation;
import com.gwtplatform.mvp.databind.client.validation.Validator;

import java.util.Iterator;

/**
 * Manager responsible for binding property accessors, validators and formatters to model properties.
 * It updates these values while the user interacts with the view.
 *
 * @author Danilo Reinert
 */
public class Binding<T> implements PropertyBinder<T>, DatabindUiHandlers, Iterable<String>, Binder {

    private final PresenterEngine<T> engine = new PresenterEngine<T>();
    private final DatabindView view;
    private T model;

    @SuppressWarnings("unchecked")
    public Binding(DatabindView view) {
        if (view == null) {
            throw new NullPointerException("The parameter _view_ cannot be null");
        }
        this.view = view;
        this.view.setUiHandlers(this);
    }

    @SuppressWarnings("unchecked")
    public Binding(DatabindView view, DatabindUiHandlers uiHandlers) {
        if (view == null) {
            throw new NullPointerException("The parameter _view_ cannot be null");
        }
        this.view = view;
        this.view.setUiHandlers(uiHandlers);
    }

    /*
    @Override
    public <F> HandlerRegistration bindProperty(String id, ProvidesValue<T, F> providesValue) {
        return engine.bind(id, providesValue);
    }

    @Override
    public <F> HandlerRegistration bindProperty(String id, ProvidesValue<T, F> providesValue,
                                                ReadFormatter<F, ?> readFormatter) {
        return engine.bind(id, providesValue, readFormatter);
    }
    */

    @Override
    public <F> HandlerRegistration bind(String id, PropertyAccessor<T, F> propertyAccessor) {
        return engine.bind(id, propertyAccessor);
    }

    @Override
    public <F> HandlerRegistration bind(String id, PropertyAccessor<T, F> propertyAccessor,
                                        Validator<T, F> validatesValue) {
        return engine.bind(id, propertyAccessor, validatesValue);
    }

    @Override
    public <F> HandlerRegistration bind(String id, PropertyAccessor<T, F> propertyAccessor,
                                        Validator<T, F> validatesValue, Formatter<F, ?> formatter) {
        return engine.bind(id, propertyAccessor, validatesValue, formatter);
    }

    @Override
    public <F> HandlerRegistration bind(String id, PropertyAccessor<T, F> propertyAccessor,
                                        Formatter<F, ?> formatter) {
        return engine.bind(id, propertyAccessor, formatter);
    }

    /*
    @Override
    public <F> HandlerRegistration bindProperty(boolean autoRefresh, String id, ProvidesValue<T, F> providesValue) {
        return engine.bind(autoRefresh, id, providesValue);
    }

    @Override
    public <F> HandlerRegistration bindProperty(boolean autoRefresh, String id, ProvidesValue<T, F> providesValue,
                                                ReadFormatter<F, ?> readFormatter) {
        return engine.bind(autoRefresh, id, providesValue, readFormatter);
    }
    */

    @Override
    public <F> HandlerRegistration bind(boolean autoRefresh, String id,
                                        PropertyAccessor<T, F> propertyAccessor) {
        return engine.bind(autoRefresh, id, propertyAccessor);
    }

    @Override
    public <F> HandlerRegistration bind(boolean autoRefresh, String id, PropertyAccessor<T, F> propertyAccessor,
                                        Formatter<F, ?> formatter) {
        return engine.bind(autoRefresh, id, propertyAccessor, formatter);
    }

    @Override
    public <F> HandlerRegistration bind(boolean autoRefresh, String id, PropertyAccessor<T, F> propertyAccessor,
                                        Validator<T, F> validatesValue) {
        return engine.bind(autoRefresh, id, propertyAccessor, validatesValue);
    }

    @Override
    public <F> HandlerRegistration bind(boolean autoRefresh, String id, PropertyAccessor<T, F> propertyAccessor,
                                        Validator<T, F> validatesValue, Formatter<F, ?> formatter) {
        return engine.bind(autoRefresh, id, propertyAccessor, validatesValue, formatter);
    }

    /**
     * Get all values from view, apply to the model and return if all of them were valid.
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

    /**
     * Get the bound model.
     *
     * @return model object
     */
    public T getModel() {
        return model;
    }

    /**
     * Get property accessor from specified property.
     *
     * @param id identification of the property
     * @return property accessor
     */
    @SuppressWarnings("unchecked")
    public <V> PropertyAccessor<T, V> getPropertyAccessor(String id) {
        return (PropertyAccessor<T, V>) engine.getPropertyAccessor(id);
    }

    /**
     * Get validator from specified property.
     *
     * @param id identification of the property
     * @return validator
     */
    @SuppressWarnings("unchecked")
    public <V> Validator<T, V> getValidator(String id) {
        return (Validator<T, V>) engine.getValidatesValue(id);
    }

    /**
     * Get property's value from model.
     *
     * @param id    identification of the property
     * @return value retrieved from model's property
     */
    @SuppressWarnings("unchecked")
    public <V> V getValue(String id) {
        return (V) engine.getRawValue(id, model);
    }

    /**
     * Get the bound view.
     *
     * @return view
     */
    public DatabindView getView() {
        return view;
    }

    /**
     * Returns {@code true} if this binding has a property accessor bound to this property id.
     *
     * @param id identification of the property
     * @return {@code true} if this property id is bound, {@code false} otherwise.
     */
    public boolean hasProperty(String id) {
        return engine.hasProperty(id);
    }

    /**
     * Informs if the property should be automatically sent to view when updating model.
     *
     * @param id identification of the property
     * @return {@code true} if property is set to view by updating the model, {@code false} otherwise
     */
    public boolean isAutoRefresh(String id) {
        return engine.isAutoRefresh(id);
    }

    /**
     * Returns an iterator over bound properties' ids.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<String> iterator() {
        return engine.iterator();
    }

    /**
     * Must be called by View when some bound widget changes value.
     * <p/>
     * Presenter must implement #DatabindUiHandlers and delegate the execution of #DatabindUiHandlers.onValueChanged
     * to this method.
     *
     * @param id    property id
     * @param value new value from view (may need unformatting)
     */
    @Override
    public void onValueChanged(String id, Object value) {
        setValueToModel(id, value);
    }

    /**
     * Send all properties' values to view.
     */
    public void refresh() {
        for (String id : engine) {
            setValueToView(id);
        }
    }

    /**
     * Send specified property's value to view.
     *
     * @param id identification of the property
     */
    public void refresh(String id) {
        if (engine.hasProperty(id)) {
            setValueToView(id);
        }
    }

    /**
     * Send all auto refresh properties' values to view.
     */
    public void refreshAutoOnly() {
        for (String id : engine) {
            if (engine.isAutoRefresh(id)) {
                setValueToView(id);
            }
        }
    }

    /**
     * Set a model to this databinding and send all bound (auto refresh) properties to view.
     *
     * @param model model object
     */
    public void setModel(T model) {
        this.model = model;
        refreshAutoOnly();
    }

    /**
     * Set value to model's property.
     *
     * @param id    identification of the property
     * @param value value to be applied
     */
    public void setValue(String id, Object value) {
        engine.setRawValue(id, model, value);
        refresh(id);
    }

    @Override
    public boolean unbind(String id) {
        return engine.unbind(id);
    }

//    /**
//     * Unbind all properties and return if all were successfully unbound.
//     *
//     * @return {@code true} if all properties were unbound, {@code false} otherwise.
//     */
//    public boolean unbind() {
//        boolean allUnbound = true;
//        for (String id : engine) {
//            if (!unbind(id)) allUnbound = false;
//        }
//        return allUnbound;
//    }

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

    /**
     * Validate and apply a value to a property and return if it was successfully set.
     * When the model is *null*, no value is set and {@code false} is returned.
     *
     * @param id    identification of the property
     * @param value value to be applied
     * @return {@code true} if the value was validated and applied, {@code false} otherwise
     */
    @SuppressWarnings("unchecked")
    private boolean setValueToModel(String id, Object value) {
        if (model == null) {
            return false;
        }

        // First, validate the value
        final Validation validation = engine.isValueValid(id, model, value);
        if (validation.isValid()) {
            // If valid, then set to the model and fire valid value event
            engine.setFormattedValue(id, model, value);
            view.onValidationSuccess(id, validation.getValidationMessage());
            return true;
        } else {
            // It must be executed only when a validation occurs and it returns invalid
            view.onValidationFailure(id, validation.getValidationMessage());
        }

        return false;
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
