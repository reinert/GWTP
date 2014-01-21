package com.gwtplatform.mvp.databind.client;


import com.google.web.bindery.event.shared.HandlerRegistration;
import com.gwtplatform.mvp.databind.client.format.Formatter;
import com.gwtplatform.mvp.databind.client.property.PropertyAccessor;
import com.gwtplatform.mvp.databind.client.validation.Validation;
import com.gwtplatform.mvp.databind.client.validation.Validator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Danilo Reinert
 */
@SuppressWarnings("unchecked")
public class PresenterEngine<T> implements PropertyBinder<T>, Iterable<String> {

    private static class PropertyBinding {

        PropertyAccessor propertyAccessor;
        Validator validator;
        Formatter formatter;
        boolean autoRefresh;
        // TODO: Add readOnly flag?
        // The readOnly flag would not allow receiving values from View.

        PropertyBinding(boolean autoRefresh, PropertyAccessor propertyAccessor, Validator validator,
                        Formatter formatter) {
            this.propertyAccessor = propertyAccessor;
            this.validator = validator;
            this.formatter = formatter;
            this.autoRefresh = autoRefresh;
        }

        PropertyBinding(boolean autoRefresh, PropertyAccessor propertyAccessor) {
            this(autoRefresh, propertyAccessor, null, null);
        }

        PropertyBinding(boolean autoRefresh, PropertyAccessor propertyAccessor, Formatter formatter) {
            this(autoRefresh, propertyAccessor, null, formatter);
        }

        PropertyBinding(boolean autoRefresh, PropertyAccessor propertyAccessor, Validator validator) {
            this(autoRefresh, propertyAccessor, validator, null);
        }
    }

    //TODO: substitute map by a simple javascript object to increase performance
    private final Map<String, PropertyBinding> properties = new HashMap<String, PropertyBinding>();

    /*
    public <F> HandlerRegistration bindProperty(String id, ProvidesValue<T, F> providesValue) {
        return bindProperty(true, id, providesValue);
    }

    @Override
    public <F> HandlerRegistration bindProperty(String id, ProvidesValue<T, F> providesValue,
                                                ReadFormatter<F, ?> readFormatter) {
        return bindProperty(true, id, providesValue, readFormatter);
    }
    */

    public <F> HandlerRegistration bind(String id, PropertyAccessor<T, F> propertyAccessor) {
        return bind(true, id, propertyAccessor);
    }

    public <F> HandlerRegistration bind(String id, PropertyAccessor<T, F> propertyAccessor,
                                        Validator<T, F> validator) {
        return bind(true, id, propertyAccessor, validator);
    }

    @Override
    public <F> HandlerRegistration bind(String id, PropertyAccessor<T, F> propertyAccessor,
                                        Validator<T, F> validator, Formatter<F, ?> formatter) {
        return bind(true, id, propertyAccessor, validator, formatter);
    }

    @Override
    public <F> HandlerRegistration bind(String id, PropertyAccessor<T, F> propertyAccessor,
                                        Formatter<F, ?> formatter) {
        return bind(true, id, propertyAccessor, formatter);
    }

    /*
    public <F> HandlerRegistration bind(boolean autoRefresh, String id, ProvidesValue<T, F> providesValue) {
        return bindProperty(autoRefresh, id, providesValue, null);
    }

    @Override
    public <F> HandlerRegistration bind(boolean autoRefresh, String id, final ProvidesValue<T, F> providesValue,
                                                final ReadFormatter<F, ?> readFormatter) {
        final PropertyAccessor<T, F> propertyAccessor = new PropertyAccessor<T, F>() {
            @Override
            public void setValue(T object, F value) {
            }

            @Override
            public F getValue(T object) {
                return providesValue.getValue(object);
            }
        };
        Formatter<F, Object> formatter = null;
        if (readFormatter != null) {
            formatter = new Formatter<F, Object>() {
                @Nullable
                @Override
                public F unformat(@Nullable Object formattedValue) {
                    return null;
                }

                @Nullable
                @Override
                public Object format(@Nullable F rawValue) {
                    return readFormatter.format(rawValue);
                }
            };
        }
        return bind(autoRefresh, id, propertyAccessor, null, formatter);
    }
    */

    public <F> HandlerRegistration bind(boolean autoRefresh, String id, PropertyAccessor<T, F> propertyAccessor) {
        return bind(autoRefresh, id, propertyAccessor, null, null);
    }

    @Override
    public <F> HandlerRegistration bind(boolean autoRefresh, String id, PropertyAccessor<T, F> propertyAccessor,
                                        Formatter<F, ?> formatter) {
        return bind(autoRefresh, id, propertyAccessor, null, formatter);
    }

    @Override
    public <F> HandlerRegistration bind(boolean autoRefresh, String id, PropertyAccessor<T, F> propertyAccessor,
                                        Validator<T, F> validator) {
        return bind(autoRefresh, id, propertyAccessor, validator, null);
    }

    @Override
    public <F> HandlerRegistration bind(boolean autoRefresh, String id, PropertyAccessor<T, F> propertyAccessor,
                                        Validator<T, F> validator, Formatter<F, ?> formatter) {
        if (properties.containsKey(id)) {
            PropertyBinding propertyBinding = properties.get(id);
            propertyBinding.propertyAccessor = propertyAccessor;
            propertyBinding.validator = validator;
            propertyBinding.formatter = formatter;
        } else {
            PropertyBinding propertyBinding = new PropertyBinding(autoRefresh, propertyAccessor, validator, formatter);
            properties.put(id, propertyBinding);
        }
        return BinderHandlerRegistration.of(this, id);
    }

    /**
     * Tells whether the specified value can be set to the property of the model.
     *
     * @param id    identification of the property
     * @param value value to be validated
     * @return validation
     */
    public Validation isValueValid(String id, T data, Object value) {
        PropertyBinding propertyBinding = properties.get(id);
        if (propertyBinding != null && propertyBinding.validator != null) {
            return propertyBinding.validator.validate(data, unformat(id, value));
        }
        return Validation.valid();
    }

    public Object getRawValue(String id, T data) {
        PropertyBinding propertyBinding = properties.get(id);
        if (propertyBinding != null) return propertyBinding.propertyAccessor.getValue(data);
        return null;
    }

    public Object getFormattedValue(String id, T data) {
        PropertyBinding propertyBinding = properties.get(id);
        if (propertyBinding != null) {
            if (propertyBinding.formatter != null) {
                return propertyBinding.formatter.format(propertyBinding.propertyAccessor.getValue(data));
            }
            return propertyBinding.propertyAccessor.getValue(data);
        }
        return null;
    }

    public void setRawValue(String id, T data, Object value) {
        PropertyBinding propertyBinding = properties.get(id);
        if (propertyBinding != null) propertyBinding.propertyAccessor.setValue(data, value);
    }

    public void setFormattedValue(String id, T data, Object value) {
        PropertyBinding propertyBinding = properties.get(id);
        if (propertyBinding != null) {
            if (propertyBinding.formatter != null) {
                propertyBinding.propertyAccessor.setValue(data, propertyBinding.formatter.unformat(value));
            } else {
                propertyBinding.propertyAccessor.setValue(data, value);
            }
        }
    }

    public PropertyAccessor<T, ?> getPropertyAccessor(String id) {
        return properties.get(id).propertyAccessor;
    }

    public Validator<T, ?> getValidatesValue(String id) {
        return properties.get(id).validator;
    }

    public boolean isAutoRefresh(String id) {
        return properties.get(id).autoRefresh;
    }

    public boolean hasProperty(String id) {
        return properties.containsKey(id);
    }

    /**
     * Returns an iterator over bound properties' ids.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<String> iterator() {
        return properties.keySet().iterator();
    }

    @Override
    public boolean unbind(String id) {
        return properties.remove(id) != null;
    }

    public Object unformat(String id, Object formattedValue) {
        PropertyBinding propertyBinding = properties.get(id);
        if (propertyBinding != null && propertyBinding.formatter != null) {
            return propertyBinding.formatter.unformat(formattedValue);
        }
        return formattedValue;
    }

    boolean isValueDifferent(String id, Object formattedValue) {
        PropertyBinding propertyBinding = properties.get(id);
        if (propertyBinding != null) {
            final Object value = propertyBinding.propertyAccessor.getValue(id);
            Object unformatedValue = formattedValue;
            if (propertyBinding.formatter != null) {
                unformatedValue = propertyBinding.formatter.unformat(formattedValue);
            }

            // If both are null then they are not different
            if (value == null && unformatedValue == null) {
                return false;
            }

            // Avoid NPE
            if (value != null) {
                return !value.equals(unformatedValue);
            }
        }
        // We cannot tell they are different because this id is not bound
        return false;
    }
}
