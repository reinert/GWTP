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

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import com.google.gwt.user.client.Window;
import com.google.inject.assistedinject.Assisted;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.carstore.client.application.cars.car.CarPresenter.MyView;
import com.gwtplatform.carstore.client.application.cars.car.navigation.NavigationTab;
import com.gwtplatform.carstore.client.application.cars.car.navigation.NavigationTabEvent;
import com.gwtplatform.carstore.client.application.cars.car.properties.CarDtoProperties;
import com.gwtplatform.carstore.client.application.cars.car.properties.CarPropertiesDtoProperties;
import com.gwtplatform.carstore.client.application.cars.event.CarAddedEvent;
import com.gwtplatform.carstore.client.application.event.ActionBarEvent;
import com.gwtplatform.carstore.client.application.event.ChangeActionBarEvent;
import com.gwtplatform.carstore.client.application.event.ChangeActionBarEvent.ActionType;
import com.gwtplatform.carstore.client.application.event.DisplayMessageEvent;
import com.gwtplatform.carstore.client.application.event.GoBackEvent;
import com.gwtplatform.carstore.client.application.widget.message.Message;
import com.gwtplatform.carstore.client.application.widget.message.MessageStyle;
import com.gwtplatform.carstore.client.place.NameTokens;
import com.gwtplatform.carstore.client.resources.CarMessages;
import com.gwtplatform.carstore.client.rest.CarService;
import com.gwtplatform.carstore.client.rest.ManufacturerService;
import com.gwtplatform.carstore.client.util.AbstractAsyncCallback;
import com.gwtplatform.carstore.client.util.ErrorHandlerAsyncCallback;
import com.gwtplatform.carstore.shared.dto.CarDto;
import com.gwtplatform.carstore.shared.dto.CarPropertiesDto;
import com.gwtplatform.carstore.shared.dto.ManufacturerDto;
import com.gwtplatform.dispatch.rest.shared.RestDispatch;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;
import com.gwtplatform.mvp.databind.client.Binding;
import com.gwtplatform.mvp.databind.client.DatabindView;

public class CarPresenter extends Presenter<MyView, CarPresenter.MyProxy>
        implements CarUiHandlers, NavigationTab, GoBackEvent.GoBackHandler, ActionBarEvent.ActionBarHandler {

    public interface MyView extends DatabindView<CarUiHandlers> {
        void setAllowedManufacturers(List<ManufacturerDto> manufacturerDtos);
    }

    public interface MyProxy extends ProxyPlace<CarPresenter> {
    }

    private final CarService carService;
    private final ManufacturerService manufacturerService;
    private final CarMessages messages;
    private final RestDispatch dispatcher;
    private final PlaceManager placeManager;
    private final CarProxyFactory carProxyFactory;
    private final Binding<CarDto> binding;
    private final Binding<CarPropertiesDto> propertiesBinding;

    @Inject
    public CarPresenter(EventBus eventBus,
                        MyView view,
                        RestDispatch dispatcher,
                        CarService carService,
                        ManufacturerService manufacturerService,
                        PlaceManager placeManager,
                        CarProxyFactory carProxyFactory,
                        CarMessages messages,
                        @Assisted MyProxy proxy,
                        @Assisted CarDto carDto) {
        super(eventBus, view, proxy);

        this.dispatcher = dispatcher;
        this.carService = carService;
        this.manufacturerService = manufacturerService;
        this.messages = messages;
        this.placeManager = placeManager;
        this.carProxyFactory = carProxyFactory;
        this.binding = new Binding<CarDto>(view);
        this.propertiesBinding = new Binding<CarPropertiesDto>(view);

        //TODO: support nesting bindings
        carDto = carDto != null ? carDto : new CarDto();
        setCarDto(carDto);

        getView().setUiHandlers(this);
    }

    @Override
    public void onGoBack(GoBackEvent event) {
        placeManager.revealPlace(new PlaceRequest.Builder().nameToken(NameTokens.getCars()).build());
    }

    @Override
    public void onActionEvent(ActionBarEvent event) {
        if (event.isTheSameToken(NameTokens.newCar)) {
            if (event.getActionType() == ActionType.DONE) {
                flush();
                onSave();
            }
        } else if (event.isTheSameToken(binding.getModel().getManufacturer().getName() + binding.getModel().getModel())) {
            if (event.getActionType() == ActionType.UPDATE) {
                flush();
                onSave();
            } else if (event.getActionType() == ActionType.DELETE) {
                onDeleteCar();
            }
        }
    }

    @Override
    public void onCancel() {
        NavigationTabEvent.fireClose(this, this);
    }

    @Override
    public void onSave() {
        // Ensure #propertiesBinding merged to #binding.
        // When View call #onSave, the values were already flushed, but we need to manually merge the two bindings.
        // Later, with nested binding support, it won't be necessary.
        // UPDATE: Not necessary if #propertiesBinding.model references #binding.model.carProperties
        //mergeBindings();

        final CarDto carDto = binding.getModel();
        dispatcher.execute(carService.saveOrCreate(carDto), new ErrorHandlerAsyncCallback<CarDto>(this) {
            @Override
            public void onSuccess(CarDto newCar) {
                onCarSaved(carDto, newCar);
            }
        });
    }

    @Override
    public String getName() {
        final CarDto carDto = binding.getModel();
        if (carDto.getId() != null) {
            return carDto.getManufacturer().getName() + " " + carDto.getModel();
        } else {
            return "New car";
        }
    }

    @Override
    public String getToken() {
        return getProxy().getNameToken();
    }

    @Override
    public boolean isClosable() {
        return true;
    }

    @Override
    public void onValueChanged(String id, Object value) {
        //TODO: create BindingManager?
        binding.onValueChanged(id, value);
        propertiesBinding.onValueChanged(id, value);
    }

    @Override
    protected void onBind() {
        addRegisteredHandler(GoBackEvent.getType(), this);
        addRegisteredHandler(ActionBarEvent.getType(), this);

        registerHandler(binding.bind("model", CarDtoProperties.MODEL));
        registerHandler(binding.bind("manufacturer", CarDtoProperties.MANUFACTURER));
        registerHandler(propertiesBinding.bind("someString", CarPropertiesDtoProperties.SOME_STRING));
        registerHandler(propertiesBinding.bind("someNumber", CarPropertiesDtoProperties.SOME_NUMBER));
        registerHandler(propertiesBinding.bind("someDate", CarPropertiesDtoProperties.SOME_DATE));
    }

    @Override
    protected void onReveal() {
        dispatcher.execute(manufacturerService.getManufacturers(), new AbstractAsyncCallback<List<ManufacturerDto>>() {
            @Override
            public void onSuccess(List<ManufacturerDto> manufacturers) {
                onGetManufacturerSuccess(manufacturers);
            }
        });

        Boolean createNew = placeManager.getCurrentPlaceRequest().matchesNameToken(NameTokens.newCar);
        List<ActionType> actions;
        if (createNew) {
            actions = Arrays.asList(ActionType.DONE);
            ChangeActionBarEvent.fire(this, actions, false);
        } else {
            actions = Arrays.asList(ActionType.DELETE, ActionType.UPDATE);
            ChangeActionBarEvent.fire(this, actions, false);
        }

        NavigationTabEvent.fireReveal(this, this);
    }

    @Override
    protected void revealInParent() {
        RevealContentEvent.fire(this, RootCarPresenter.TYPE_SetCarContent, this);
    }

    private void onGetManufacturerSuccess(List<ManufacturerDto> manufacturerDtos) {
        getView().setAllowedManufacturers(manufacturerDtos);
        refresh();
    }

    private void flush() {
        //TODO: support nesting bindings
        propertiesBinding.flush();
        binding.flush();
        //mergeBindings();
    }

    // Not necessary if #propertiesBinding.model references #binding.model.carProperties
//    private void mergeBindings() {
//        binding.getModel().setCarProperties(propertiesBinding.getModel());
//    }

    private void onCarSaved(CarDto oldCar, CarDto newCar) {
        DisplayMessageEvent.fire(CarPresenter.this, new Message(messages.carSaved(), MessageStyle.SUCCESS));
        CarAddedEvent.fire(CarPresenter.this, newCar, oldCar.getId() == null);

        if (oldCar.getId() == null) {
            final CarDto carDto = new CarDto();
            setCarDto(carDto);

            MyProxy proxy = carProxyFactory.create(newCar, newCar.getManufacturer().getName() + newCar.getModel());

            placeManager.revealPlace(new PlaceRequest.Builder().nameToken(proxy.getNameToken()).build());
        }
    }

    private void onDeleteCar() {
        final CarDto carDto = binding.getModel();
        Boolean confirm = Window.confirm("Are you sure you want to delete " + carDto.getModel() + "?");
        if (confirm) {
            dispatcher.execute(carService.delete(carDto.getId()), new ErrorHandlerAsyncCallback<Void>(this) {
                @Override
                public void onSuccess(Void nothing) {
                    NavigationTabEvent.fireClose(CarPresenter.this, CarPresenter.this);
                }
            });
        }
    }

    private void refresh() {
        binding.refresh();
        propertiesBinding.refresh();
    }

    private void setCarDto(CarDto carDto) {
        binding.setModel(carDto);
        // #binding.model.carProperties will always be updated since it references #propertiesBinding.model
        // which ensures the updated state.
        propertiesBinding.setModel(carDto.getCarProperties());
    }
}
