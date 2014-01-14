package com.gwtplatform.mvp.databind.client;


import com.gwtplatform.mvp.client.UiHandlers;

/**
 * @author Danilo Reinert
 */
public interface DatabindUiHandlers extends UiHandlers {

    /**
     * Should be called by View when some bound widget has changed value.
     *
     * @param id property id
     * @param value new value from view (may need to be unformatted)
     * @return
     */
    boolean onValueChanged(String id, Object value);
}
