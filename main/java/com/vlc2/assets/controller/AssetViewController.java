package com.vlc2.assets.controller;

import com.vlc2.assets.aspect.Authorized;
import com.vlc2.assets.dto.request.AssetViewHistorySearch;
import com.vlc2.assets.entity.AssetView;
import com.vlc2.assets.entity.Type;
import com.vlc2.assets.service.AssetViewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/assetView")
public class AssetViewController {
    @Autowired
    AssetViewService assetViewService;


    @PostMapping()
    @Authorized
    List<AssetView> findAllData(@RequestBody AssetViewHistorySearch search) {
        log.info("Enter into findAllData method - AssetViewController");

        try {
            List<AssetView> listAssetView = assetViewService.findHistoryByAssetType(search);
            log.info("End findAllData method - AssetViewController");
            return listAssetView;
        } catch (Exception e) {
            log.error("Enter into findAllData method - AssetViewController {} is null", search);
            return null;
        }


    }


}
