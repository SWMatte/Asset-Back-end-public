package com.vlc2.assets.service;

import com.vlc2.assets.dto.request.AssetViewHistorySearch;
import com.vlc2.assets.entity.AssetView;

import java.util.List;

public interface AssetViewService {


    List<AssetView> findHistoryByAssetType(AssetViewHistorySearch search) throws Exception;
}
