/*
 * Copyright Txus Ballesteros 2016 (@txusballesteros)
 *
 * This file is part of some open source application.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 * Contact: Txus Ballesteros <txus.ballesteros@gmail.com>
 */
package com.txusballesteros.testing.view;

import com.txusballesteros.testing.R;
import com.txusballesteros.testing.presentation.MainPresenter;
import com.txusballesteros.testing.presentation.model.ImageModel;
import com.txusballesteros.testing.view.internal.di.ActivityModule;
import com.txusballesteros.testing.view.internal.di.DaggerActivityComponent;
import com.txusballesteros.testing.view.internal.di.ViewModule;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AbsActivity implements MainPresenter.View {
    @Inject MainPresenter presenter;

    @Override
    int onRequestLayout() {
        return R.layout.activity_main;
    }

    @Override
    void onInitializeInjection() {
        DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .viewModule(new ViewModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void renderDashboard(List<ImageModel> images) { }

    @Override
    public void renderError() { }
}
