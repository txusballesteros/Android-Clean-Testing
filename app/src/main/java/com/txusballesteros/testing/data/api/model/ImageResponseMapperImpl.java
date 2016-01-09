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
package com.txusballesteros.testing.data.api.model;

import android.net.Uri;

import com.txusballesteros.testing.data.api.endpoint.model.DashboardEndpointResponse;
import com.txusballesteros.testing.data.api.endpoint.model.DashboardListEndpointResponse;
import com.txusballesteros.testing.data.datasource.model.ImageEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ImageResponseMapperImpl implements ImageResponseMapper {
    @Inject
    public ImageResponseMapperImpl() { }

    @Override
    public List<ImageResponse> map(DashboardListEndpointResponse source) {
        List<ImageResponse> result = new ArrayList<>(source.data.size());
        for (int location = 0; location < source.data.size(); location++) {
            final ImageResponse image = map(source.data.get(location));
            result.add(image);
        }
        return result;
    }

    @Override
    public List<ImageEntity> map(List<ImageResponse> source) {
        List<ImageEntity> result = new ArrayList<>(source.size());
        for (int location = 0; location < source.size(); location++) {
            final ImageEntity image = map(source.get(location));
            result.add(image);
        }
        return result;
    }

    @Override
    public ImageEntity map(ImageResponse source) {
        return new ImageEntity.Builder()
                .setUrl(source.getUrl())
                .build();
    }

    @Override
    public ImageResponse map(DashboardEndpointResponse source) {
        return new ImageResponse.Builder()
                .setUrl(Uri.parse(String.format("http://i.giphy.com/%s.gif", source.id)))
                .build();
    }
}
