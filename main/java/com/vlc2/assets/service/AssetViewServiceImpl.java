package com.vlc2.assets.service;

import com.vlc2.assets.dto.request.AssetViewHistorySearch;
import com.vlc2.assets.entity.AssetStatus;
import com.vlc2.assets.entity.AssetView;
import com.vlc2.assets.repository.AssetViewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AssetViewServiceImpl implements AssetViewService {

    @Autowired
    AssetViewRepository assetViewRepository;

    @Override
    public List<AssetView> findHistoryByAssetType(AssetViewHistorySearch search) throws Exception {
        log.info("Enter into findHistoryByAssetType method - AssetViewServiceImpl");
        if (search != null) {
            if (search.getStatus().isEmpty()) {
                log.info("End findHistoryByAssetType method - AssetViewServiceImpl ");
                return assetViewRepository.listAssetView(search.getType(), search.getSearch());
            } else {
                log.info("End findHistoryByAssetType method - AssetViewServiceImpl ");
                return assetViewRepository.listAssetViewWithStatus(search.getType(), search.getStatus(), search.getSearch());
            }
        } else {
            log.error("Error in findHistoryByAssetType method - AssetViewServiceImpl ");

            throw new Exception();
        }


    }
}
