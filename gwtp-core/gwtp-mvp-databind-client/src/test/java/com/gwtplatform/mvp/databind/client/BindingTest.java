package com.gwtplatform.mvp.databind.client;

import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.HasValue;
import com.gwtplatform.mvp.databind.client.mock.DatabindViewMock;
import com.gwtplatform.mvp.databind.client.mock.HasValueMock;
import com.gwtplatform.mvp.databind.client.mock.TakesValueMock;
import com.gwtplatform.mvp.databind.client.property.DatePropertyAccessor;
import com.gwtplatform.mvp.databind.client.property.ProvidesInt;
import com.gwtplatform.mvp.databind.client.property.TextPropertyAccessor;
import junit.framework.TestCase;

import javax.annotation.Nullable;
import java.util.Date;

/**
 * @author Danilo Reinert
 */
public class BindingTest extends TestCase {

    static class Model {
        String stringValue;
        Integer intValue;
        Date dateValue;
    }

    public void testBindPropertyWithPropertyAccessorOnly() {
        //===================================================================
        // COMMON
        //===================================================================

        final String stringProperty = "stringProperty";
        final String intProperty = "intProperty";
        final String dateProperty = "dateProperty";


        //===================================================================
        // VIEW
        //===================================================================

        // Create view
        final DatabindView mockView = new DatabindViewMock();

        // Create widgets
        final HasValue<String> stringWidget = new HasValueMock<String>();
        final TakesValue<Integer> intWidget = new TakesValueMock<Integer>();
        final HasValue<Date> dateWidget = new HasValueMock<Date>();

        // Bind widgets
        mockView.bindWidget(stringProperty, stringWidget);
        mockView.bindReadOnlyWidget(intProperty, intWidget);
        mockView.bindWidget(dateProperty, dateWidget);


        //===================================================================
        // PRESENTER
        //===================================================================

        final Binding<Model> binding = new Binding<Model>(mockView);

        // Bind accessors
        binding.bindProperty(stringProperty, new TextPropertyAccessor<Model>() {
            @Override
            public void setValue(Model model, @Nullable String value) {
                model.stringValue = value;
            }

            @Nullable
            @Override
            public String getValue(Model model) {
                return model.stringValue;
            }
        });
        binding.bindProperty(intProperty, new ProvidesInt<Model>() {
            @Nullable
            @Override
            public Integer getValue(Model model) {
                return model.intValue;
            }
        });
        binding.bindProperty(false, dateProperty, new DatePropertyAccessor<Model>() {
            @Override
            public void setValue(Model model, @Nullable Date value) {
                model.dateValue = value;
            }

            @Nullable
            @Override
            public Date getValue(Model model) {
                return model.dateValue;
            }
        });


        // Assign new model
        final Model model = new Model();
        final String initialString = "initialString";
        model.stringValue = initialString;
        final Integer initialInt = 1;
        model.intValue = initialInt;
        final Date initialDate = new Date();
        model.dateValue = initialDate;
        binding.setModel(model);


        // Assert initial values at View
        // String value at widget and view
        assertEquals(initialString, stringWidget.getValue());
        assertEquals(initialString, mockView.getValue(stringProperty));
        // Integer value at widget and view
        assertEquals(initialInt, intWidget.getValue());
        assertEquals(initialInt, mockView.getValue(intProperty));
        // Date was bound as not auto refresh. So view must return null to dateProperty.
        assertNull(dateWidget.getValue());
        assertNull(mockView.getValue(dateProperty));


        // Manual refresh particular property
        binding.refresh(dateProperty);


        // Assert date value at widget and view
        assertEquals(initialDate, dateWidget.getValue());
        assertEquals(initialDate, mockView.getValue(dateProperty));


        // Widgets at View get new values
        final String expectedString = "newString";
        stringWidget.setValue(expectedString);
        final Date expectedDate = new Date();
        dateWidget.setValue(expectedDate);


        // Assert new values at Presenter
        assertEquals(expectedString, binding.getModel().stringValue);
        assertEquals(expectedDate, binding.getModel().dateValue);
    }
}
