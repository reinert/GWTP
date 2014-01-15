package com.gwtplatform.mvp.databind.client;

import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * A default HandlerRegistrarion implementation to be used by {@link Binder}s.
 *
 * @author Danilo Reinert
 */
public class BinderHandlerRegistration implements HandlerRegistration {

    /**
     * Creates a {@link BinderHandlerRegistration} bound with other HandlerRegistrations.
     * By the time of unbinding, all handlers will be removed.
     *
     * @param binder container
     * @param id identification of the binding
     * @param handlerRegistrations handlers to be chained removed
     * @return the composite handler created
     */
    public static HandlerRegistration of(Binder binder, String id,
                                         HandlerRegistration... handlerRegistrations) {
        return new BinderHandlerRegistration(binder, id, handlerRegistrations);
    }

    /**
     * Creates a {@link BinderHandlerRegistration} associated with given id.
     *
     * @param binder container
     * @param id identification of the binding
     * @return the handler created
     */
    public static HandlerRegistration of(Binder binder, String id) {
        return new BinderHandlerRegistration(binder, id, null);
    }

    private final Binder binder;
    private final HandlerRegistration[] handlerRegistrations;
    private final String id;

    private BinderHandlerRegistration(Binder binder, String id,
                                      HandlerRegistration[] handlerRegistrations) {
        this.binder = binder;
        this.id = id;
        this.handlerRegistrations = handlerRegistrations;
    }

    @Override
    public void removeHandler() {
        if (handlerRegistrations != null) {
            // If there were other handlers bound to this one, then remove them
            for (HandlerRegistration handlerRegistration : handlerRegistrations) {
                handlerRegistration.removeHandler();
            }
        }
        binder.unbind(id);
    }
}
