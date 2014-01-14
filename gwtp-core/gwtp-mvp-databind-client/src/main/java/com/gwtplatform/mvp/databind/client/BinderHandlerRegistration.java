package com.gwtplatform.mvp.databind.client;

import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * @author Danilo Reinert
 */
public class BinderHandlerRegistration implements HandlerRegistration {

    public static HandlerRegistration of(Binder binder, String id,
                                         HandlerRegistration... handlerRegistrations) {
        return new BinderHandlerRegistration(binder, id, handlerRegistrations);
    }

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
            for (HandlerRegistration handlerRegistration : handlerRegistrations) {
                handlerRegistration.removeHandler();
            }
        }
        binder.unbind(id);
    }
}
