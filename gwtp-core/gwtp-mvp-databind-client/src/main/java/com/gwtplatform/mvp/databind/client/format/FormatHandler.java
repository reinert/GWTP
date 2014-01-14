package com.gwtplatform.mvp.databind.client.format;

/**
 * @author Danilo Reinert
 */
public interface FormatHandler {

    <MODEL, VIEW> VIEW formatValue(String id, MODEL value);

    <MODEL, VIEW> MODEL unformatValue(String id, VIEW value);
}
