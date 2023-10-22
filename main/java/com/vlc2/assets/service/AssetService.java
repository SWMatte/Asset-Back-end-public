package com.vlc2.assets.service;


import com.vlc2.assets.dto.request.AssetsCreateRequest;
import com.vlc2.assets.entity.User;

import java.util.List;

public interface AssetService {

    void add(AssetsCreateRequest element, User utente) throws Exception;




}
