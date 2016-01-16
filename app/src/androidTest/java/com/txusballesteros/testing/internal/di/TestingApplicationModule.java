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
package com.txusballesteros.testing.internal.di;

import com.txusballesteros.testing.Application;
import com.txusballesteros.testing.data.api.endpoint.internal.di.EndpointsModule;
import com.txusballesteros.testing.data.api.internal.di.ApiModule;
import com.txusballesteros.testing.data.cache.internal.di.CachesModule;
import com.txusballesteros.testing.data.datasource.internal.di.DatasourcesModule;
import com.txusballesteros.testing.data.internal.di.RepositoriesModule;
import com.txusballesteros.testing.threading.JobExecutor;
import com.txusballesteros.testing.threading.PostExecutionThread;
import com.txusballesteros.testing.threading.ThreadExecutor;
import com.txusballesteros.testing.threading.UIThread;
import com.txusballesteros.testing.view.instrumentation.internal.di.InstrumentationModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module ( includes = {
        InstrumentationModule.class,
        RepositoriesModule.class,
        DatasourcesModule.class,
        CachesModule.class,
        ApiModule.class,
        EndpointsModule.class
})
public class TestingApplicationModule implements ApplicationModule {
    private final Application application;

    public TestingApplicationModule(Application application) {
        this.application = application;
    }

    @Override @Provides
    public Application provideApplication() {
        return application;
    }

    @Override @Provides @Singleton
    public ThreadExecutor provideThreadExecutor(JobExecutor executor) {
        return executor;
    }

    @Override @Provides @Singleton
    public PostExecutionThread providePostExecutionThread() {
        return UIThread.getInstance();
    }

    @Override @Provides @Singleton
    public DependenciesInjector provideDependenciesInjector() {
        return new TestingDependenciesInjector();
    }
}
