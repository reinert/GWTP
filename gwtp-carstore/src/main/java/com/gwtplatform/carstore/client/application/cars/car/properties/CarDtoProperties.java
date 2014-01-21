package com.gwtplatform.carstore.client.application.cars.car.properties;

import com.gwtplatform.carstore.shared.dto.CarDto;
import com.gwtplatform.carstore.shared.dto.ManufacturerDto;
import com.gwtplatform.mvp.databind.client.property.PropertyAccessor;
import com.gwtplatform.mvp.databind.client.property.TextPropertyAccessor;

import javax.annotation.Nullable;

/**
 * @author Danilo Reinert
 */
public class CarDtoProperties {

    //================================================================================
    // Property Accessors
    //================================================================================

    public static final TextPropertyAccessor<CarDto> MODEL = new TextPropertyAccessor<CarDto>() {
        @Override
        public void setValue(CarDto carDto, @Nullable String value) {
            carDto.setModel(value);
        }

        @Nullable
        @Override
        public String getValue(CarDto carDto) {
            return carDto.getModel();
        }
    };

    public static final PropertyAccessor<CarDto,ManufacturerDto> MANUFACTURER = new PropertyAccessor<CarDto,
            ManufacturerDto>() {
        @Override
        public void setValue(CarDto carDto, @Nullable ManufacturerDto value) {
            carDto.setManufacturer(value);
        }

        @Nullable
        @Override
        public ManufacturerDto getValue(CarDto carDto) {
            return carDto.getManufacturer();
        }
    };

    //================================================================================
    // Validators
    //================================================================================

    //================================================================================
    // Formatters
    //================================================================================

}
