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

import com.txusballesteros.testing.data.cache.DashboardCache;
import com.txusballesteros.testing.data.cache.model.ImageCache;
import com.txusballesteros.testing.data.cache.model.ImageCacheMapper;
import com.txusballesteros.testing.data.datasource.DashboardDatasource;
import com.txusballesteros.testing.data.datasource.model.ImageEntity;
import com.txusballesteros.testing.data.datasource.model.ImageEntityMapper;
import com.txusballesteros.testing.domain.model.Image;
import com.txusballesteros.testing.domain.repository.DashboardRepository;

import java.util.List;

import javax.inject.Inject;

public class DashboardRepositoryImpl extends AbsRepository implements DashboardRepository {
    private final DashboardDatasource datasource;
    private final DashboardCache cache;
    private final ImageEntityMapper entityMapper;
    private final ImageCacheMapper cacheMapper;

    @Inject
    public DashboardRepositoryImpl(DashboardDatasource datasource,
                                   DashboardCache cache,
                                   ImageEntityMapper entityMapper,
                                   ImageCacheMapper cacheMapper) {
        this.datasource = datasource;
        this.cache = cache;
        this.entityMapper = entityMapper;
        this.cacheMapper = cacheMapper;
    }

    @Override
    public void getDashboard(final Callback callback) {
        try {
            List<Image> result;
            if (cache.isValid()) {
                final List<ImageCache> images = cache.get();
                result = cacheMapper.map(images);
            } else {
                final List<ImageEntity> images = datasource.getDashboard();
                result = entityMapper.map(images);
            }
            callback.onSuccess(result);
        } catch (Exception error) {
            callback.onError();
        }
    }
}
