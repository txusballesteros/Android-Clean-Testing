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
package com.txusballesteros.testing.presentation.model;

import android.net.Uri;

import com.txusballesteros.testing.UnitTest;
import com.txusballesteros.testing.domain.model.Image;

import org.junit.Test;
import org.mockito.Mock;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;

public class ImageModelMapperUnitTest extends UnitTest {
    private final static Uri TESTING_IMAGE_URL = Uri.parse("http://developer.android.com");
    @Mock Image imageMock;
    @Mock List<Image> imageListMock;
    @Mock Iterator<Image> imageIteratorMock;
    private ImageModelMapper mapper;

    @Override
    protected void onSetup() {
        mapper = new ImageModelMapperImpl();
    }

    @Test
    public void shouldMapOneImage() {
        doReturn(TESTING_IMAGE_URL).when(imageMock).getUrl();

        final ImageModel mapResult = mapper.map(imageMock);

        assertEquals(TESTING_IMAGE_URL, mapResult.getUrl());
    }

    @Test
    public void shouldMapListOfImages() {
        doReturn(TESTING_IMAGE_URL).when(imageMock).getUrl();
        doReturn(imageMock).when(imageListMock).get(eq(0));
        doReturn(1).when(imageListMock).size();
        doReturn(true).when(imageIteratorMock).hasNext();
        doReturn(imageIteratorMock).when(imageListMock).iterator();

        final List<ImageModel> mapResult = mapper.map(imageListMock);

        assertNotNull(mapResult);
        assertFalse(mapResult.isEmpty());
        assertEquals(imageListMock.size(), mapResult.size());
    }
}
