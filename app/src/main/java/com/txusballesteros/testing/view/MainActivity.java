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

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.txusballesteros.testing.R;
import com.txusballesteros.testing.presentation.MainPresenter;
import com.txusballesteros.testing.presentation.model.ImageModel;
import com.txusballesteros.testing.view.instrumentation.ImageLoader;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class MainActivity extends AbsActivity implements MainPresenter.View {
    @Inject MainPresenter presenter;
    @Inject ImageLoader imageLoader;
    @Bind(R.id.list) RecyclerView listView;
    private DashboardListAdapter listAdapter;

    @Override
    int onRequestLayout() {
        return R.layout.activity_main;
    }

    @Override
    void onViewReady() {
        initializeList();
    }

    private void initializeList() {
        listAdapter = new DashboardListAdapter(this, imageLoader);
        listView.setLayoutManager(new GridLayoutManager(this, 2));
        listView.setHasFixedSize(true);
        listView.setAdapter(listAdapter);
    }

    @Override
    void onInitializeInjection() {
        getDependenciesInjector().inject(this);
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
    public void renderDashboard(List<ImageModel> images) {
        listAdapter.addAll(images);
    }

    @Override
    public void renderError() { }
}
