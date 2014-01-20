package com.gwtplatform.mvp.databind.client;


import com.gwtplatform.mvp.client.UiHandlers;

/**
 * An interface that must be implemented by any {@link com.gwtplatform.mvp.client.PresenterWidget}
 * which should support Databinding.
 *
 * @author Danilo Reinert
 */
public interface DatabindUiHandlers extends UiHandlers {

    /**
     * Must be called by View when some bound widget changes value.
     *
     * @param id    property id
     * @param value new value from view (may need unformatting)
     */
    void onValueChanged(String id, Object value);
}
