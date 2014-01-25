package com.gwtplatform.carstore.client.application.manufacturer.properties;

import com.github.reinert.ko.databind.client.property.TextPropertyAccessor;
import com.gwtplatform.carstore.shared.dto.ManufacturerDto;


import javax.annotation.Nullable;

/**
 * @author Danilo Reinert
 */
public class ManufacturerDtoProperties {

    //================================================================================
    // Property Accessors
    //================================================================================

    public static final TextPropertyAccessor<ManufacturerDto> NAME = new TextPropertyAccessor<ManufacturerDto>() {
        @Override
        public void setValue(ManufacturerDto manufacturerDto, @Nullable String value) {
            manufacturerDto.setName(value);
        }

        @Nullable
        @Override
        public String getValue(ManufacturerDto manufacturerDto) {
            return manufacturerDto.getName();
        }
    };

    //================================================================================
    // Validators
    //================================================================================

    //================================================================================
    // Formatters
    //================================================================================

}
