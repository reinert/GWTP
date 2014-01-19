package com.gwtplatform.mvp.databind.client;

import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.HandlerRegistration;
import junit.framework.TestCase;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;

/**
 * @author Danilo Reinert
 */
public class BindingTest extends TestCase {

    public void testBindReadOnlyWidget() {
        final DatabindViewEngine delegate = new DatabindViewEngine();

        final DatabindView mockView = mock(DatabindView.class);

        when(mockView.bindWidget(anyString(), any(HasValue.class))).then(new Answer<HandlerRegistration>() {
            @Override
            public HandlerRegistration answer(InvocationOnMock invocation) throws Throwable {
                return delegate.bindWidget((String)invocation.getArguments()[0], (HasValue)invocation.getArguments()[1]);
            }
        });
        when(mockView.bindReadOnlyWidget(anyString(), any(TakesValue.class))).then(new Answer<HandlerRegistration>() {
            @Override
            public HandlerRegistration answer(InvocationOnMock invocation) throws Throwable {
                return delegate.bindReadOnlyWidget((String)invocation.getArguments()[0], (TakesValue)invocation.getArguments()[1]);
            }
        });

        final String expectedValue = "newValue";

        final TakesValue<String> hasString = mock(TakesValue.class, withSettings().extraInterfaces(IsWidget.class));
        when(hasString.getValue()).thenReturn(expectedValue);

        final String id = "someProperty";
        mockView.bindReadOnlyWidget(id, hasString);

        assertEquals(expectedValue, delegate.getValue(id));
    }
}
