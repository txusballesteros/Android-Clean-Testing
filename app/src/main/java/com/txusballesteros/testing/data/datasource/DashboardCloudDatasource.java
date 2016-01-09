package com.txusballesteros.testing.data.datasource;

import com.txusballesteros.testing.data.api.DashboardApi;
import com.txusballesteros.testing.data.api.model.ImageResponse;
import com.txusballesteros.testing.data.api.model.ImageResponseMapper;
import com.txusballesteros.testing.data.datasource.model.ImageEntity;

import java.util.List;

import javax.inject.Inject;

public class DashboardCloudDatasource extends AbsDatasource implements DashboardDatasource {
    private final DashboardApi api;
    private final ImageResponseMapper mapper;

    @Inject
    public DashboardCloudDatasource(DashboardApi api, ImageResponseMapper mapper) {
        this.api = api;
        this.mapper = mapper;
    }

    @Override
    public List<ImageEntity> getDashboard() {
        final List<ImageResponse> images = api.getDashboard();
        return mapper.map(images);
    }
}
