/**
 * Copyright 2011 ArcBees Inc.
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

package com.gwtplatform.mvp.client;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

/**
 * A simple implementation of {@link com.gwtplatform.mvp.client.View} that simply disregard every call to
 * {@link #setInSlot(Object, com.google.gwt.user.client.ui.IsWidget)}, {@link #addToSlot(Object, com.google.gwt.user.client.ui.IsWidget)}, and
 * {@link #removeFromSlot(Object, com.google.gwt.user.client.ui.IsWidget)}.
 * <p/>
 * Feel free not to inherit from this if you need another base class (such as
 * {@link com.google.gwt.user.client.ui.Composite}), but you will have to define
 * the above methods.
 * <p/>
 * * <b>Important</b> call {@link #initWidget(com.google.gwt.user.client.ui.Widget)} in your {@link View}'s
 * constructor.
 */
public abstract class ViewImpl implements View {
    private Widget widget;

    @Override
    public void addToSlot(Object slot, IsWidget content) {
    }

    @Override
    public void removeFromSlot(Object slot, IsWidget content) {
    }

    @Override
    public void setInSlot(Object slot, IsWidget content) {
    }

    @Override
    public Widget asWidget() {
        return widget;
    }

    @Override
    public void finish() {
        History.back();
    }

    protected void initWidget(Widget widget) {
        this.widget = widget;
    }
}
