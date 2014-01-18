package com.gwtplatform.mvp.databind.client;


import com.google.web.bindery.event.shared.HandlerRegistration;
import com.gwtplatform.mvp.databind.client.format.Formatter;
import com.gwtplatform.mvp.databind.client.format.ReadFormatter;
import com.gwtplatform.mvp.databind.client.property.PropertyAccessor;
import com.gwtplatform.mvp.databind.client.property.ProvidesValue;
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

    private static class Holder {

        PropertyAccessor propertyAccessor;
        Validator validatesValue;
        Formatter formatter;
        boolean autoBind;

        Holder(boolean autoBind, PropertyAccessor propertyAccessor, Validator validatesValue, Formatter formatter) {
            this.propertyAccessor = propertyAccessor;
            this.validatesValue = validatesValue;
            this.formatter = formatter;
            this.autoBind = autoBind;
        }

        Holder(boolean autoBind, PropertyAccessor propertyAccessor) {
            this(autoBind, propertyAccessor, null, null);
        }

        Holder(boolean autoBind, PropertyAccessor propertyAccessor, Formatter formatter) {
            this(autoBind, propertyAccessor, null, formatter);
        }

        Holder(boolean autoBind, PropertyAccessor propertyAccessor, Validator validatesValue) {
            this(autoBind, propertyAccessor, validatesValue, null);
        }
    }

    //TODO: substitute map by a simple javascript object to increase performance
    private final Map<String, Holder> holderMap = new HashMap<String, Holder>();

    public <F> HandlerRegistration bindProperty(String id, PropertyAccessor<T, F> propertyAccessor) {
        return bindProperty(id, propertyAccessor, null, null);
    }

    public <F> HandlerRegistration bindProperty(boolean autoBind, String id, PropertyAccessor<T, F> propertyAccessor) {
        return bindProperty(autoBind, id, propertyAccessor, null, null);
    }

    public <F> HandlerRegistration bindProperty(boolean autoBind, String id, final ProvidesValue<T, F> providesValue) {
        return bindProperty(autoBind, id, new PropertyAccessor<T, F>() {
            @Override
            public void setValue(T object, F value) {
            }

            @Override
            public F getValue(T object) {
                return providesValue.getValue(object);
            }
        }, null, null);
    }

    public <F> HandlerRegistration bindProperty(String id, final ProvidesValue<T, F> providesValue) {
        return bindProperty(id, providesValue, null);
    }

    @Override
    public <F> HandlerRegistration bindProperty(String id, ProvidesValue<T, F> providesValue, ReadFormatter<F, ?> readFormatter) {
        return bindProperty(true, id, providesValue, readFormatter);
    }

    @Override
    public <F> HandlerRegistration bindProperty(String id, PropertyAccessor<T, F> propertyAccessor, Formatter<F, ?> formatter) {
        return bindProperty(true, id, propertyAccessor, formatter);
    }

    public <F> HandlerRegistration bindProperty(String id, PropertyAccessor<T, F> propertyAccessor, Validator<T, F> validatesValue) {
        return bindProperty(true, id, propertyAccessor, validatesValue, null);
    }

    @Override
    public <F> HandlerRegistration bindProperty(String id, PropertyAccessor<T, F> propertyAccessor, Validator<T, F> validatesValue,
                                 Formatter<F, ?> formatter) {
        return bindProperty(true, id, propertyAccessor, validatesValue, formatter);
    }

    @Override
    public <F> HandlerRegistration bindProperty(boolean autoBind, String id, final ProvidesValue<T, F> providesValue,
                                 ReadFormatter<F, ?> readFormatter) {
        return bindProperty(id, new PropertyAccessor<T, F>() {
            @Override
            public void setValue(T object, F value) {
            }

            @Override
            public F getValue(T object) {
                return providesValue.getValue(object);
            }
        }, readFormatter);
    }

    @Override
    public <F> HandlerRegistration bindProperty(boolean autoBind, String id, PropertyAccessor<T, F> propertyAccessor,
                                 Formatter<F, ?> formatter) {
        return bindProperty(autoBind, id, propertyAccessor, null, null);
    }

    @Override
    public <F> HandlerRegistration bindProperty(boolean autoBind, String id, PropertyAccessor<T, F> propertyAccessor,
                                 Validator<T, F> validatesValue) {
        return bindProperty(autoBind, id, propertyAccessor, validatesValue, null);
    }

    @Override
    public <F> HandlerRegistration bindProperty(boolean autoBind, String id, PropertyAccessor<T, F> propertyAccessor,
                                 Validator<T, F> validatesValue, Formatter<F, ?> formatter) {
        if (holderMap.containsKey(id)) {
            Holder tHolder = holderMap.get(id);
            tHolder.propertyAccessor = propertyAccessor;
            tHolder.validatesValue = validatesValue;
            tHolder.formatter = formatter;
        } else {
            Holder tHolder = new Holder(autoBind, propertyAccessor, validatesValue, formatter);
            holderMap.put(id, tHolder);
        }
        return BinderHandlerRegistration.of(this, id);
    }

    public Validation isValueValid(String id, T data, Object value) {
        Holder holder = holderMap.get(id);
        if (holder != null && holder.validatesValue != null) {
            return holder.validatesValue.validate(data, unformat(id, value));
        }
        return Validation.valid();
    }

    public Object getRawValue(String id, T data) {
        Holder holder = holderMap.get(id);
        if (holder != null) return holder.propertyAccessor.getValue(data);
        return null;
    }

    public Object getFormattedValue(String id, T data) {
        Holder holder = holderMap.get(id);
        if (holder != null) {
            if (holder.formatter != null) {
                return holder.formatter.format(holder.propertyAccessor.getValue(data));
            }
            return holder.propertyAccessor.getValue(data);
        }
        return null;
    }

    public void setRawValue(String id, T data, Object value) {
        Holder holder = holderMap.get(id);
        if (holder != null) holder.propertyAccessor.setValue(data, value);
    }

    public void setFormattedValue(String id, T data, Object value) {
        Holder holder = holderMap.get(id);
        if (holder != null) {
            if (holder.formatter != null) {
                holder.propertyAccessor.setValue(data, holder.formatter.unformat(value));
            } else {
                holder.propertyAccessor.setValue(data, value);
            }
        }
    }

    public PropertyAccessor<T, ?> getPropertyAccessor(String id) {
        return holderMap.get(id).propertyAccessor;
    }

    public Validator<T, ?> getValidatesValue(String id) {
        return holderMap.get(id).validatesValue;
    }

    public boolean isAutoBind(String id) {
        return holderMap.get(id).autoBind;
    }

    public boolean hasProperty(String id) {
        return holderMap.containsKey(id);
    }

    /**
     * Returns an iterator over bound properties' ids.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<String> iterator() {
        return holderMap.keySet().iterator();
    }

    @Override
    public boolean unbind(String id) {
        return holderMap.remove(id) != null;
    }

    public Object unformat(String id, Object formattedValue) {
        Holder holder = holderMap.get(id);
        if (holder != null && holder.formatter != null) {
            return holder.formatter.unformat(formattedValue);
        }
        return formattedValue;
    }

    boolean isValueDifferent(String id, Object formattedValue) {
        Holder holder = holderMap.get(id);
        if (holder != null) {
            final Object value = holder.propertyAccessor.getValue(id);
            Object unformatedValue = formattedValue;
            if (holder.formatter != null) {
                unformatedValue = holder.formatter.unformat(formattedValue);
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
