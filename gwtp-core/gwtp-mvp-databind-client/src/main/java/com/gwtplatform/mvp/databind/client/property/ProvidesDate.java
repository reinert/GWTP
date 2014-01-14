package com.gwtplatform.mvp.databind.client.property;

import javax.annotation.Nullable;
import java.util.Date;

/**
 * Created at 08/08/13 19:06
 *
 * @author Danilo Reinert
 */

public interface ProvidesDate<T> extends ProvidesValue<T, Date> {

    public @Nullable Date getValue(T t);
}
