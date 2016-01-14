/* * Copyright Txus Ballesteros 2016 (@txusballesteros)
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

import com.txusballesteros.testing.UnitTest;
import com.txusballesteros.testing.domain.model.Image;
import com.txusballesteros.testing.domain.repository.DashboardRepository;
import com.txusballesteros.testing.domain.usecase.GetDashboardUseCase;
import com.txusballesteros.testing.threading.PostExecutionThread;
import com.txusballesteros.testing.threading.ThreadExecutor;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class GetDashboardInteractorUnitTest extends UnitTest {
    @Mock ThreadExecutor threadExecutorMock;
    @Mock PostExecutionThread postExecutionThreadMock;
    @Mock DashboardRepository dashboardRepositoryMock;
    @Mock GetDashboardUseCase.Callback callbackMock;
    @Mock List<Image> repositoryResultMock;
    private GetDashboardUseCase interactor;

    @Override
    protected void onSetup() {
        interactor = new GetDashboardInteractor(threadExecutorMock,
                                                postExecutionThreadMock,
                                                dashboardRepositoryMock);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((Runnable)invocation.getArguments()[0]).run();
                return null;
            }
        }).when(threadExecutorMock).execute(any(Runnable.class));
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((Runnable)invocation.getArguments()[0]).run();
                return null;
            }
        }).when(postExecutionThreadMock).post(any(Runnable.class));
    }

    @Test
    public void shouldExecute() {
        interactor.execute(callbackMock);

        verify(threadExecutorMock).execute(any(Runnable.class));
        verify(dashboardRepositoryMock).getDashboard(any(DashboardRepository.Callback.class));
        verifyNoMoreInteractions(dashboardRepositoryMock);
        verifyNoMoreInteractions(threadExecutorMock);
    }

    @Test
    public void shouldPostOnDashboardReady() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((DashboardRepository.Callback)invocation.getArguments()[0])
                                                    .onSuccess(repositoryResultMock);
                return null;
            }
        }).when(dashboardRepositoryMock).getDashboard(any(DashboardRepository.Callback.class));

        interactor.execute(callbackMock);

        verify(postExecutionThreadMock).post(any(Runnable.class));
        verify(callbackMock).onDashboardReady(eq(repositoryResultMock));
    }

    @Test
    public void shouldPostOnError() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((DashboardRepository.Callback)invocation.getArguments()[0]).onError();
                return null;
            }
        }).when(dashboardRepositoryMock).getDashboard(any(DashboardRepository.Callback.class));

        interactor.execute(callbackMock);

        verify(postExecutionThreadMock).post(any(Runnable.class));
        verify(callbackMock).onError();
    }
}
