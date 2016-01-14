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

import com.txusballesteros.testing.UnitTest;
import com.txusballesteros.testing.domain.model.Image;
import com.txusballesteros.testing.domain.usecase.GetDashboardUseCase;
import com.txusballesteros.testing.presentation.model.ImageModel;
import com.txusballesteros.testing.presentation.model.ImageModelMapper;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class MainPresenterUnitTest extends UnitTest {
    @Mock MainPresenter.View viewMock;
    @Mock GetDashboardUseCase interactorMock;
    @Mock ImageModelMapper mapperMock;
    @Mock List<Image> interactorResultMock;
    @Mock List<ImageModel> mapperResultMock;
    protected MainPresenter presenter;

    @Override
    protected void onSetup() {
        presenter = new MainPresenterImpl(viewMock, interactorMock, mapperMock);
    }

    @Test
    public void shouldOnStart() {
        presenter.onStart();

        verify(interactorMock).execute(any(GetDashboardUseCase.Callback.class));
        verifyNoMoreInteractions(interactorMock);
    }

    @Test
    public void shouldCallViewRenderDashboard() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((GetDashboardUseCase.Callback)invocation.getArguments()[0])
                                                            .onDashboardReady(interactorResultMock);
                return null;
            }
        }).when(interactorMock).execute(any(GetDashboardUseCase.Callback.class));
        doReturn(mapperResultMock).when(mapperMock).map(eq(interactorResultMock));

        presenter.onStart();

        verify(mapperMock).map(eq(interactorResultMock));
        verify(viewMock).renderDashboard(eq(mapperResultMock));
        verifyNoMoreInteractions(viewMock);
    }

    @Test
    public void shouldCallViewRenderError() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((GetDashboardUseCase.Callback)invocation.getArguments()[0]).onError();
                return null;
            }
        }).when(interactorMock).execute(any(GetDashboardUseCase.Callback.class));

        presenter.onStart();

        verify(viewMock).renderError();
        verifyNoMoreInteractions(viewMock);
    }
}
