package com.gwtplatform.mvp.databind.client;

import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.HasValue;
import com.gwtplatform.mvp.databind.client.mock.HasValueMock;
import junit.framework.TestCase;

/**
 * @author Danilo Reinert
 */
public class DatabindViewEngineTest extends TestCase {

    interface BindWidgetCallback {
        void bind(DatabindViewEngine engine, String propertyId, TakesValue widget);
    }

    public void testBindReadOnlyWidget() {
        doTestBindWidget(new BindWidgetCallback() {
            @Override
            public void bind(DatabindViewEngine engine, String propertyId, TakesValue widget) {
                engine.bind(propertyId, widget);
            }
        });
    }

    public void testBindWidget() {
        doTestBindWidget(new BindWidgetCallback() {
            @Override
            public void bind(DatabindViewEngine engine, String propertyId, TakesValue widget) {
                engine.bind(propertyId, (HasValue<?>) widget, Strategy.ON_CHANGE);
            }
        });
    }

    private void doTestBindWidget(BindWidgetCallback callback) {
        /*
        The binding must be tested performing both #DatabindViewEngine.getValue and #DatabindViewEngine.setValue.
         */
        final DatabindViewEngine engine = new DatabindViewEngine();

        final String propertyId = "someProperty";
        final String initialValue = "initialValue";

        final HasValue<String> takesString = new HasValueMock<String>();
        takesString.setValue(initialValue);

        // Bind the widget to the view
        callback.bind(engine, propertyId, takesString);

        // Assert the view delegates the value to the bound widget
        assertEquals(initialValue, engine.getValue(propertyId));

        final String newValue = "newValue";

        // Perform set operation through the view
        engine.setValue(propertyId, newValue);

        // Assert the previous set operation was successful
        assertEquals(newValue, engine.getValue(propertyId));
    }
}
