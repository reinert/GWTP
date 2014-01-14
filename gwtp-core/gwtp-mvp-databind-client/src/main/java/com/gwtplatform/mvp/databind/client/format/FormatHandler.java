package com.gwtplatform.mvp.databind.client.format;

/**
 * @author Danilo Reinert
 */
public interface FormatHandler {

    <MODEL, VIEW> VIEW formatValue(MODEL value);

    <MODEL, VIEW> MODEL unformatValue(VIEW value);
}
