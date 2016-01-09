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
package com.txusballesteros.testing.presentation;

import com.txusballesteros.testing.domain.model.Image;
import com.txusballesteros.testing.domain.usecase.GetDashboardUseCase;
import com.txusballesteros.testing.presentation.model.ImageModelMapper;

import java.util.List;

import javax.inject.Inject;

public class MainPresenterImpl implements MainPresenter {
    private final View view;
    private final GetDashboardUseCase interactor;
    private final ImageModelMapper mapper;

    @Inject
    public MainPresenterImpl(View view, GetDashboardUseCase interactor, ImageModelMapper mapper) {
        this.view = view;
        this.interactor = interactor;
        this.mapper = mapper;
    }

    @Override
    public void onStart() {
        interactor.execute(new GetDashboardUseCase.Callback() {
            @Override
            public void onDashboardReady(List<Image> images) {
                view.renderDashboard(mapper.map(images));
            }

            @Override
            public void onError() {
                view.renderError();
            }
        });
    }

    @Override
    public void onStop() { }
}
