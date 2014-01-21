/**
 * Copyright 2013 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.gwtplatform.carstore.client.application.cars.car;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.gwtplatform.carstore.client.application.cars.car.CarPresenter.MyView;
import com.gwtplatform.carstore.client.application.manufacturer.ui.ManufacturerRenderer;
import com.gwtplatform.carstore.shared.dto.ManufacturerDto;
import com.gwtplatform.mvp.databind.client.DatabindViewImpl;

import javax.inject.Inject;
import java.util.List;

public class CarView extends DatabindViewImpl<CarUiHandlers> implements MyView {

    interface Binder extends UiBinder<Widget, CarView> {
    }

    @UiField
    TextBox model;
    @UiField(provided = true)
    ValueListBox<ManufacturerDto> manufacturer;
    @UiField
    TextBox someString;
    @UiField
    IntegerBox someNumber;
    @UiField
    DateBox someDate;

    @Inject
    CarView(Binder uiBinder) {
        manufacturer = new ValueListBox<ManufacturerDto>(new ManufacturerRenderer());

        initWidget(uiBinder.createAndBindUi(this));

        someString.getElement().setAttribute("placeholder", "Property #1");
        someNumber.getElement().setAttribute("placeholder", "Property #2");
        someDate.getElement().setAttribute("placeholder", "Property #3");

        bind("model", model);
        bind("manufacturer", manufacturer);
        bind("someString", someString);
        bind("someNumber", someNumber);
        bind("someDate", someDate);
    }

    @Override
    public void setAllowedManufacturers(List<ManufacturerDto> manufacturerDtos) {
        manufacturer.setValue(manufacturerDtos.isEmpty() ? null : manufacturerDtos.get(0));
        manufacturer.setAcceptableValues(manufacturerDtos);
    }

    @UiHandler("save")
    void onSaveClicked(ClickEvent ignored) {
        getUiHandlers().onSave();
    }

    @UiHandler("close")
    void onCancelClicked(ClickEvent ignored) {
        getUiHandlers().onCancel();
    }
}
