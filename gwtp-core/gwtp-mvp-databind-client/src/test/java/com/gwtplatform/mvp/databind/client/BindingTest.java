package com.gwtplatform.mvp.databind.client;

import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.HasValue;
import com.gwtplatform.mvp.databind.client.format.Formatter;
import com.gwtplatform.mvp.databind.client.format.ReadFormatter;
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

    public void testBindPropertyWithAccessorOnly() {
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

    public void testBindPropertyWithFormatter() {
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
        final HasValue<Double> doubleWidget = new HasValueMock<Double>();
        final TakesValue<String> stringWidget = new TakesValueMock<String>();
        final HasValue<Long> longWidget = new HasValueMock<Long>();

        // Bind widgets
        mockView.bindWidget(stringProperty, doubleWidget);
        mockView.bindReadOnlyWidget(intProperty, stringWidget);
        mockView.bindWidget(dateProperty, longWidget);


        //===================================================================
        // PRESENTER
        //===================================================================

        final Binding<Model> binding = new Binding<Model>(mockView);

        // Declare accessors
        final TextPropertyAccessor<Model> stringPropertyAccessor = new TextPropertyAccessor<Model>() {
            @Override
            public void setValue(Model model, @Nullable String value) {
                model.stringValue = value;
            }

            @Nullable
            @Override
            public String getValue(Model model) {
                return model.stringValue;
            }
        };
        final ProvidesInt<Model> intProvidesValue = new ProvidesInt<Model>() {
            @Nullable
            @Override
            public Integer getValue(Model model) {
                return model.intValue;
            }
        };
        final DatePropertyAccessor<Model> datePropertyAccessor = new DatePropertyAccessor<Model>() {
            @Override
            public void setValue(Model model, @Nullable Date value) {
                model.dateValue = value;
            }

            @Nullable
            @Override
            public Date getValue(Model model) {
                return model.dateValue;
            }
        };

        // Declare formatters
        final Formatter<String, Double> stringPropertyFormatter = new Formatter<String, Double>() {
            @Nullable
            @Override
            public String unformat(@Nullable Double formattedValue) {
                return formattedValue != null ? String.valueOf(formattedValue) : null;
            }

            @Nullable
            @Override
            public Double format(@Nullable String rawValue) {
                return rawValue != null ? Double.valueOf(rawValue) : null;
            }
        };
        final ReadFormatter<Number, String> intPropertyFormatter = new ReadFormatter<Number, String>() {
            @Nullable
            @Override
            public String format(@Nullable Number rawValue) {
                return rawValue != null ? String.valueOf(rawValue) : null;
            }
        };
        final Formatter<Date, Long> datePropertyFormatter = new Formatter<Date, Long>() {
            @Nullable
            @Override
            public Date unformat(@Nullable Long formattedValue) {
                return formattedValue != null ? new Date(formattedValue) : null;
            }

            @Nullable
            @Override
            public Long format(@Nullable Date rawValue) {
                return rawValue != null ? rawValue.getTime() : null;
            }
        };

        // Bind properties
        binding.bindProperty(stringProperty, stringPropertyAccessor, stringPropertyFormatter);
        binding.bindProperty(intProperty, intProvidesValue, intPropertyFormatter);
        binding.bindProperty(false, dateProperty, datePropertyAccessor, datePropertyFormatter);


        // Assign new model
        final Model model = new Model();
        final String initialString = "2.35";
        model.stringValue = initialString;
        final Integer initialInt = 1;
        model.intValue = initialInt;
        final Date initialDate = new Date();
        model.dateValue = initialDate;
        binding.setModel(model);


        // Assert initial values at View
        // String value at widget and view
        assertEquals(initialString, binding.getValue(stringProperty));
        assertEquals(stringPropertyFormatter.format(initialString), doubleWidget.getValue());
        assertEquals(stringPropertyFormatter.format(initialString), mockView.getValue(stringProperty));
        assertEquals(initialString, stringPropertyFormatter.unformat(doubleWidget.getValue()));
        assertEquals(initialString, stringPropertyFormatter.unformat(mockView.<Double>getValue(stringProperty)));

        // Integer value at widget and view
        assertEquals(initialInt, binding.getValue(intProperty));
        assertEquals(intPropertyFormatter.format(initialInt), stringWidget.getValue());
        assertEquals(intPropertyFormatter.format(initialInt), mockView.getValue(intProperty));

        // Date was bound as not auto refresh. So view must return null to dateProperty.
        assertEquals(initialDate, binding.getValue(dateProperty));
        assertNull(longWidget.getValue());
        assertNull(mockView.getValue(dateProperty));


        // Manual refresh particular property
        binding.refresh(dateProperty);


        // Assert date value at widget and view
        assertEquals(datePropertyFormatter.format(initialDate), longWidget.getValue());
        assertEquals(datePropertyFormatter.format(initialDate), mockView.getValue(dateProperty));
        assertEquals(initialDate, datePropertyFormatter.unformat(longWidget.getValue()));
        assertEquals(initialDate, datePropertyFormatter.unformat(mockView.<Long>getValue(dateProperty)));


        // Widgets at View get new values
        final String expectedStringPropertyValue = "5.41";
        final Date expectedDatePropertyValue = new Date();

        doubleWidget.setValue(Double.valueOf(expectedStringPropertyValue));
        longWidget.setValue(expectedDatePropertyValue.getTime());


        // Assert new values at Presenter
        assertEquals(expectedStringPropertyValue, binding.getValue(stringProperty));
        assertEquals(expectedDatePropertyValue, binding.getValue(dateProperty));
        assertEquals(expectedStringPropertyValue, binding.getModel().stringValue);
        assertEquals(expectedDatePropertyValue, binding.getModel().dateValue);


        // Model receives new values at Presenter
        final Double expectedStringPropertyValueAtView = 6.38;
        final String expectedIntPropertyValueAtView = "543";
        final Long expectedDatePropertyValueAtView = new Date().getTime();

        binding.setValue(stringProperty, String.valueOf(expectedStringPropertyValueAtView));
        binding.setValue(dateProperty, new Date(expectedDatePropertyValueAtView));

        // ProvidesValue does not allow to set value from binding
        //binding.setValue(intProperty, Integer.valueOf(expectedIntPropertyValueAtView));
        model.intValue = Integer.valueOf(expectedIntPropertyValueAtView);
        binding.refresh(intProperty);


        // Assert new values at View
        assertEquals(expectedStringPropertyValueAtView, doubleWidget.getValue());
        assertEquals(expectedStringPropertyValueAtView, mockView.getValue(stringProperty));

        assertEquals(expectedIntPropertyValueAtView, stringWidget.getValue());
        assertEquals(expectedIntPropertyValueAtView, mockView.getValue(intProperty));

        assertEquals(expectedDatePropertyValueAtView, longWidget.getValue());
        assertEquals(expectedDatePropertyValueAtView, mockView.getValue(dateProperty));
    }
}
