package com.gwtplatform.mvp.databind.client;

/**
 * An object that implements this interface should be a widget with
 * values mapped by String keys, named ids. These ids are useful for identifying
 * source properties in Presenters with databind support.
 *
 * @author Danilo Reinert
 **/
public interface HasBindingValues {

    <F> F getValue(String id);

    <F> void setValue(String id, F value);
}
