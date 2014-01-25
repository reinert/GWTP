package com.gwtplatform.carstore.client.application.cars.car.properties;

import com.github.reinert.ko.databind.client.property.DatePropertyAccessor;
import com.github.reinert.ko.databind.client.property.IntPropertyAccessor;
import com.github.reinert.ko.databind.client.property.TextPropertyAccessor;
import com.gwtplatform.carstore.shared.dto.CarPropertiesDto;

import javax.annotation.Nullable;
import java.util.Date;

/**
 * @author Danilo Reinert
 */
public class CarPropertiesDtoProperties {

    //================================================================================
    // Property Accessors
    //================================================================================

    public static final TextPropertyAccessor<CarPropertiesDto> SOME_STRING =
            new TextPropertyAccessor<CarPropertiesDto>() {
                @Override
                public void setValue(CarPropertiesDto carPropertiesDto, @Nullable String value) {
                    carPropertiesDto.setSomeString(value);
                }

                @Nullable
                @Override
                public String getValue(CarPropertiesDto carPropertiesDto) {
                    return carPropertiesDto.getSomeString();
                }
            };

    public static final IntPropertyAccessor<CarPropertiesDto> SOME_NUMBER =
            new IntPropertyAccessor<CarPropertiesDto> () {
                @Override
                public void setValue(CarPropertiesDto carPropertiesDto, @Nullable Number value) {
                    carPropertiesDto.setSomeNumber((Integer) value);
                }

                @Nullable
                @Override
                public Integer getValue(CarPropertiesDto carPropertiesDto) {
                    return carPropertiesDto.getSomeNumber();
                }
            };

    public static final DatePropertyAccessor<CarPropertiesDto> SOME_DATE =
            new DatePropertyAccessor<CarPropertiesDto>() {
                @Override
                public void setValue(CarPropertiesDto carPropertiesDto, @Nullable Date value) {
                    carPropertiesDto.setSomeDate(value);
                }

                @Nullable
                @Override
                public Date getValue(CarPropertiesDto carPropertiesDto) {
                    return carPropertiesDto.getSomeDate();
                }
            };

    //================================================================================
    // Validators
    //================================================================================

    //================================================================================
    // Formatters
    //================================================================================
}
