package com.vlc2.assets.dto.request;

import com.vlc2.assets.entity.Asset;
import com.vlc2.assets.entity.AssetStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssetsCreateRequest {

    private Asset asset;
    private int idCompany;
    private int idEmployee;
    private AssetStatus status;
    private boolean createHistory;
}
