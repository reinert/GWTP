package com.gwtplatform.mvp.databind.client;


import com.google.web.bindery.event.shared.HandlerRegistration;
import com.gwtplatform.mvp.databind.client.format.Formatter;
import com.gwtplatform.mvp.databind.client.format.ReadFormatter;
import com.gwtplatform.mvp.databind.client.property.PropertyAccessor;
import com.gwtplatform.mvp.databind.client.property.ProvidesValue;
import com.gwtplatform.mvp.databind.client.validation.Validation;
import com.gwtplatform.mvp.databind.client.validation.Validator;

import javax.annotation.Nullable;
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
        Validator validator;
        Formatter formatter;
        boolean autoRefresh;

        Holder(boolean autoRefresh, PropertyAccessor propertyAccessor, Validator validator, Formatter formatter) {
            this.propertyAccessor = propertyAccessor;
            this.validator = validator;
            this.formatter = formatter;
            this.autoRefresh = autoRefresh;
        }

        Holder(boolean autoRefresh, PropertyAccessor propertyAccessor) {
            this(autoRefresh, propertyAccessor, null, null);
        }

        Holder(boolean autoRefresh, PropertyAccessor propertyAccessor, Formatter formatter) {
            this(autoRefresh, propertyAccessor, null, formatter);
        }

        Holder(boolean autoRefresh, PropertyAccessor propertyAccessor, Validator validator) {
            this(autoRefresh, propertyAccessor, validator, null);
        }
    }

    //TODO: substitute map by a simple javascript object to increase performance
    private final Map<String, Holder> holderMap = new HashMap<String, Holder>();

    public <F> HandlerRegistration bindProperty(String id, ProvidesValue<T, F> providesValue) {
        return bindProperty(true, id, providesValue);
    }

    @Override
    public <F> HandlerRegistration bindProperty(String id, ProvidesValue<T, F> providesValue,
                                                ReadFormatter<F, ?> readFormatter) {
        return bindProperty(true, id, providesValue, readFormatter);
    }

    public <F> HandlerRegistration bindProperty(String id, PropertyAccessor<T, F> propertyAccessor) {
        return bindProperty(true, id, propertyAccessor);
    }

    public <F> HandlerRegistration bindProperty(String id, PropertyAccessor<T, F> propertyAccessor,
                                                Validator<T, F> validator) {
        return bindProperty(true, id, propertyAccessor, validator);
    }

    @Override
    public <F> HandlerRegistration bindProperty(String id, PropertyAccessor<T, F> propertyAccessor,
                                                Validator<T, F> validator, Formatter<F, ?> formatter) {
        return bindProperty(true, id, propertyAccessor, validator, formatter);
    }

    @Override
    public <F> HandlerRegistration bindProperty(String id, PropertyAccessor<T, F> propertyAccessor,
                                                Formatter<F, ?> formatter) {
        return bindProperty(true, id, propertyAccessor, formatter);
    }

    public <F> HandlerRegistration bindProperty(boolean autoRefresh, String id, ProvidesValue<T, F> providesValue) {
        return bindProperty(autoRefresh, id, providesValue, null);
    }

    @Override
    public <F> HandlerRegistration bindProperty(boolean autoRefresh, String id, final ProvidesValue<T, F> providesValue,
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
        return bindProperty(autoRefresh, id, propertyAccessor, null, formatter);
    }

    public <F> HandlerRegistration bindProperty(boolean autoRefresh, String id, PropertyAccessor<T, F> propertyAccessor) {
        return bindProperty(autoRefresh, id, propertyAccessor, null, null);
    }

    @Override
    public <F> HandlerRegistration bindProperty(boolean autoRefresh, String id, PropertyAccessor<T, F> propertyAccessor,
                                                Formatter<F, ?> formatter) {
        return bindProperty(autoRefresh, id, propertyAccessor, null, formatter);
    }

    @Override
    public <F> HandlerRegistration bindProperty(boolean autoRefresh, String id, PropertyAccessor<T, F> propertyAccessor,
                                 Validator<T, F> validator) {
        return bindProperty(autoRefresh, id, propertyAccessor, validator, null);
    }

    @Override
    public <F> HandlerRegistration bindProperty(boolean autoRefresh, String id, PropertyAccessor<T, F> propertyAccessor,
                                 Validator<T, F> validator, Formatter<F, ?> formatter) {
        if (holderMap.containsKey(id)) {
            Holder holder = holderMap.get(id);
            holder.propertyAccessor = propertyAccessor;
            holder.validator = validator;
            holder.formatter = formatter;
        } else {
            Holder holder = new Holder(autoRefresh, propertyAccessor, validator, formatter);
            holderMap.put(id, holder);
        }
        return BinderHandlerRegistration.of(this, id);
    }

    public Validation isValueValid(String id, T data, Object value) {
        Holder holder = holderMap.get(id);
        if (holder != null && holder.validator != null) {
            return holder.validator.validate(data, unformat(id, value));
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
        return holderMap.get(id).validator;
    }

    public boolean isAutoRefresh(String id) {
        return holderMap.get(id).autoRefresh;
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
