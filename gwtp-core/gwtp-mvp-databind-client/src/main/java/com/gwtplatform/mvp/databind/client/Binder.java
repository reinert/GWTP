package com.gwtplatform.mvp.databind.client;

/**
 * It defines an object that supports binding.
 * <p/>
 * The underlying implementation should provide methods for binding instances to ids
 * and must support an #unbind method that cancels the binding of some id.
 *
 * @author Danilo Reinert
 */
public interface Binder {

    /**
     * Releases any binding previously made with the given id.
     *
     * @param id id of the binding
     * @return {@code true} if any binding associated with the id was removed, {@code false} otherwise.
     */
    boolean unbind(String id);
}
