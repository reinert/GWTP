package com.gwtplatform.mvp.databind.client;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.View;

/**
 * @author Danilo Reinert
 */
public interface DatabindView<H extends DatabindUiHandlers> extends View, HasUiHandlers<H>, HasBindingValues,
        WidgetBinder, DatabindValidationHandler /* , ValidationHandlerBinder */ {
}
