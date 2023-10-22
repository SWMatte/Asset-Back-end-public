package com.vlc2.assets.controller;

import com.vlc2.assets.aspect.Authorized;


import com.vlc2.assets.dto.request.AssetsCreateRequest;
import com.vlc2.assets.entity.User;
import com.vlc2.assets.service.AssetService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asset")
@Slf4j
public class AssetController {

    @Autowired
    AssetService assetService;

    @PostMapping()
    @Authorized
    public ResponseEntity<?> addElement(@RequestBody AssetsCreateRequest asset, User user  ) {

        try {
            log.info("Enter into  - Asset controller");
            assetService.add(asset,user);
            log.info("End  add element - Asset controller");
             return ResponseEntity.ok("Add Asset complete " + HttpStatus.OK);

        } catch (Exception e) {
            log.error("Error into AssetController : " + e.getLocalizedMessage());
             return ResponseEntity.badRequest().body("Error in add Asset "+HttpStatus.BAD_REQUEST);

        }

    }




}
