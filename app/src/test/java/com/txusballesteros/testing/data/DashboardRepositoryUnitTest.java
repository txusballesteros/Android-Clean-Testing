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
package com.txusballesteros.testing.data;

import com.txusballesteros.testing.UnitTest;
import com.txusballesteros.testing.data.cache.DashboardCache;
import com.txusballesteros.testing.data.datasource.DashboardDatasource;
import com.txusballesteros.testing.data.datasource.model.ImageEntity;
import com.txusballesteros.testing.data.datasource.model.ImageEntityMapper;
import com.txusballesteros.testing.domain.model.Image;
import com.txusballesteros.testing.domain.repository.DashboardRepository;

import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

public class DashboardRepositoryUnitTest extends UnitTest {
    @Mock DashboardDatasource datasourceMock;
    @Mock DashboardCache cacheMock;
    @Mock ImageEntityMapper mapperMock;
    @Mock DashboardRepository.Callback callbackMock;
    @Mock List<ImageEntity> entitiesListMock;
    @Mock List<Image> resultMock;
    @Mock RuntimeException exceptionMock;
    private DashboardRepository repository;

    @Override
    protected void onSetup() {
        repository = new DashboardRepositoryImpl(datasourceMock, cacheMock, mapperMock);
    }

    @Test
    public void shouldGetDashboardFromCache() {
        doReturn(true).when(cacheMock).isValid();
        doReturn(resultMock).when(mapperMock).map(anyListOf(ImageEntity.class));

        repository.getDashboard(callbackMock);

        verify(cacheMock).get();
        verify(callbackMock).onSuccess(eq(resultMock));
    }

    @Test
    public void shouldGetDashboardFromDatasource() {
        doReturn(false).when(cacheMock).isValid();
        doReturn(entitiesListMock).when(datasourceMock).getDashboard();
        doReturn(resultMock).when(mapperMock).map(anyListOf(ImageEntity.class));

        repository.getDashboard(callbackMock);

        verify(datasourceMock).getDashboard();
        verify(callbackMock).onSuccess(eq(resultMock));
    }

    @Test
    public void shouldGetDashboardStoreInCache() {
        doReturn(false).when(cacheMock).isValid();
        doReturn(entitiesListMock).when(datasourceMock).getDashboard();
        doReturn(resultMock).when(mapperMock).map(anyListOf(ImageEntity.class));

        repository.getDashboard(callbackMock);

        verify(cacheMock).cache(eq(entitiesListMock));
    }

    @Test
    public void shouldGetDashboardFail() {
        doThrow(exceptionMock).when(cacheMock).isValid();

        repository.getDashboard(callbackMock);

        verify(callbackMock).onError();
    }
}
