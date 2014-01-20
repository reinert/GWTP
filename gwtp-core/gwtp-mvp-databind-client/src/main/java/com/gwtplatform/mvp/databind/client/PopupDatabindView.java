package com.gwtplatform.mvp.databind.client;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PopupView;

/**
 * @author Danilo Reinert
 */
public interface PopupDatabindView<H extends DatabindUiHandlers> extends PopupView, HasUiHandlers<H>, HasBindingValues,
        WidgetBinder, ValidationHandlerBinder, DatabindValidationHandler {
}
