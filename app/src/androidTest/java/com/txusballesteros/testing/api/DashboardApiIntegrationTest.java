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
package com.txusballesteros.testing.api;

import com.txusballesteros.testing.IntegrationTest;
import com.txusballesteros.testing.data.api.DashboardApi;
import com.txusballesteros.testing.data.api.model.ImageResponse;
import com.txusballesteros.testing.internal.di.DaggerIntegrationTestComponent;

import org.junit.Test;

import java.util.List;

import javax.inject.Inject;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;

public class DashboardApiIntegrationTest extends IntegrationTest {
    @Inject DashboardApi api;

    @Override
    protected void onInitializeInjection() {
        DaggerIntegrationTestComponent.builder()
                .build()
                .inject(this);
    }

    @Test
    public void shouldGetDashboard() {
        final List<ImageResponse> response = api.getDashboard();

        assertNotNull(response);
        assertFalse(response.isEmpty());
    }
}
