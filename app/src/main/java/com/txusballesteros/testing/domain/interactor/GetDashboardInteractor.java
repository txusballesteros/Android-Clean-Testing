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
package com.txusballesteros.testing.domain.interactor;

import com.txusballesteros.testing.domain.model.Image;
import com.txusballesteros.testing.domain.repository.DashboardRepository;
import com.txusballesteros.testing.domain.usecase.GetDashboardUseCase;
import com.txusballesteros.testing.threading.PostExecutionThread;
import com.txusballesteros.testing.threading.ThreadExecutor;

import java.util.List;

import javax.inject.Inject;

public class GetDashboardInteractor extends AbsInteractor implements GetDashboardUseCase, Runnable {
    private final ThreadExecutor executor;
    private final PostExecutionThread postExecution;
    private final DashboardRepository repository;
    private Callback callback;

    @Inject
    public GetDashboardInteractor(ThreadExecutor executor,
                                  PostExecutionThread postExecution,
                                  DashboardRepository repository) {
        this.executor = executor;
        this.postExecution = postExecution;
        this.repository = repository;
    }

    @Override
    public void execute(final Callback callback) {
        this.callback = callback;
        this.executor.execute(this);
    }

    @Override
    public void run() {
        repository.getDashboard(new DashboardRepository.Callback() {
            @Override
            public void onSuccess(final List<Image> images) {
                notifyOnSuccess(images);
            }

            @Override
            public void onError() {
                notifyOnError();
            }
        });
    }

    private void notifyOnSuccess(final List<Image> images) {
        postExecution.post(new Runnable() {
            @Override
            public void run() {
                callback.onDashboardReady(images);
            }
        });
    }

    protected void notifyOnError() {
        postExecution.post(new Runnable() {
            @Override
            public void run() {
                callback.onError();
            }
        });
    }
}
