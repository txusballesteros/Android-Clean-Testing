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
package com.txusballesteros.testing.data.api;

import com.txusballesteros.testing.UnitTest;
import com.txusballesteros.testing.data.api.endpoint.DashboardEndpoint;
import com.txusballesteros.testing.data.api.endpoint.model.DashboardListEndpointResponse;
import com.txusballesteros.testing.data.api.model.ImageResponseMapper;

import org.junit.Test;
import org.mockito.Mock;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

public class DashboardRetrofitApiUnitTest extends UnitTest {
    @Mock DashboardEndpoint endpointMock;
    @Mock ImageResponseMapper mapperMock;
    @Mock Call<DashboardListEndpointResponse> apiCallerMock;
    @Mock DashboardListEndpointResponse dashboardListEndpointResponseMock;
    private Response<DashboardListEndpointResponse> responseMock;
    private DashboardApi dashboardApi;

    @Override
    protected void onSetup() {
        responseMock = Response.success(dashboardListEndpointResponseMock);
        dashboardApi = new DashboardRetrofitApi(endpointMock, mapperMock);
    }

    @Test
    public void shouldGetDashboard() {
        try {
            doReturn(apiCallerMock).when(endpointMock).getDashboard(eq(AbsRetrofitApi.API_KEY));
            doReturn(responseMock).when(apiCallerMock).execute();

            dashboardApi.getDashboard();

            verify(endpointMock).getDashboard(eq(AbsRetrofitApi.API_KEY));
            verify(apiCallerMock).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
