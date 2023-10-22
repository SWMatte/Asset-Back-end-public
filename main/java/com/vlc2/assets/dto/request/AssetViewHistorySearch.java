package com.vlc2.assets.dto.request;

import com.vlc2.assets.entity.AssetStatus;
import com.vlc2.assets.entity.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssetViewHistorySearch {
    private Type type;
    private String search;
    private List<AssetStatus> status;

}
