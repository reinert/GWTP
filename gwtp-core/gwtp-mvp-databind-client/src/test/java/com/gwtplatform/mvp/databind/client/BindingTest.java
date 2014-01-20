package com.gwtplatform.mvp.databind.client;

import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.HasValue;
import com.gwtplatform.mvp.databind.client.mock.DatabindViewMock;
import com.gwtplatform.mvp.databind.client.mock.HasValueMock;
import junit.framework.TestCase;

/**
 * @author Danilo Reinert
 */
public class BindingTest extends TestCase {

    interface BindCallback {
        void bind(DatabindView view, String propertyId, TakesValue widget);
    }

    public void testBindReadOnlyWidget() {
        doTestBind(new BindCallback() {
            @Override
            public void bind(DatabindView view, String propertyId, TakesValue widget) {
                view.bindReadOnlyWidget(propertyId, widget);
            }
        });
    }

    public void testBindWidget() {
        doTestBind(new BindCallback() {
            @Override
            public void bind(DatabindView view, String propertyId, TakesValue widget) {
                view.bindWidget(propertyId, (HasValue<?>) widget);
            }
        });
    }

    private void doTestBind(BindCallback callback) {
        /*
        The binding must be tested performing both #DatabindView.getValue and #DatabindView.setValue.
         */
        final DatabindView mockView = new DatabindViewMock();

        final String propertyId = "someProperty";
        final String initialValue = "initialValue";

        final HasValue<String> takesString = new HasValueMock<String>();
        takesString.setValue(initialValue);

        // Bind the widget to the view
        callback.bind(mockView, propertyId, takesString);

        // Assert the view delegates the value to the bound widget
        assertEquals(initialValue, mockView.getValue(propertyId));

        final String newValue = "newValue";

        // Perform set operation through the view
        mockView.setValue(propertyId, newValue);

        // Assert the previous set operation was successful
        assertEquals(newValue, mockView.getValue(propertyId));
    }
}
