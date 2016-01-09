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
package com.txusballesteros.testing.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.txusballesteros.testing.R;
import com.txusballesteros.testing.presentation.model.ImageModel;
import com.txusballesteros.testing.view.instrumentation.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DashboardListAdapter extends RecyclerView.Adapter<DashboardListAdapter.ViewHolder> {
    private final List<ImageModel> dataset;
    private final Context context;
    private final ImageLoader imageLoader;

    public DashboardListAdapter(Context context, ImageLoader imageLoader) {
        this.context = context;
        this.imageLoader = imageLoader;
        this.dataset = new ArrayList<>();
    }

    public void addAll(List<ImageModel> images) {
        dataset.clear();
        dataset.addAll(images);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context)
                                .inflate(R.layout.dashboard_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.renderImage(dataset.get(position));
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public final class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.image) ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void renderImage(ImageModel model) {
            imageLoader.loadImageAsGif(model.getUrl(), imageView);
        }
    }
}
