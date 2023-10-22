package com.vlc2.assets.controller;

import com.vlc2.assets.aspect.Authorized;
import com.vlc2.assets.entity.History;
import com.vlc2.assets.service.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/history")
public class HistoryController {

    private HistoryService historyService;

    public HistoryController(HistoryService historyService){
        this.historyService = historyService;
    }

    @Authorized
    @GetMapping("/asset")
    public ResponseEntity<?> findHistoryByIdAsset(@RequestParam int idAsset){
        log.info("Start findHistoryByIdAsset in HistoryController...");
        try{
            List<History> found = historyService.findHistoryByIdAsset(idAsset);
            log.info("End findHistoryByIdAsset in HistoryController");
            return ResponseEntity.ok(found);
        }catch (Exception e){
            log.error("Error findHistoryByIdAsset in HistoryController: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Internal Server Error");
        }
    }

    @Authorized
    @GetMapping("/employee")
    public ResponseEntity<?> findHistoryByIdEmployee(@RequestParam int idEmployee){
        log.info("Start findHistoryByIdEmployee in HistoryController...");
        try{
            List<History> found = historyService.findHistoryByIdEmployee(idEmployee);
            log.info("End findHistoryByIdEmployee in HistoryController");
            return ResponseEntity.ok(found);
        }catch (Exception e){
            log.error("Error findHistoryByIdEmployee in HistoryController: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Internal Server Error");
        }
    }

    @Authorized
    @GetMapping("/documents")
    public ResponseEntity<?> findAllSignedDocuments(){
        try{
            List<History> found = historyService.findAllSignedDocuments();
            log.info("End findAllSignedDocument Method in HistoryController");
            return ResponseEntity.ok(found);
        }catch (Exception e){
            log.error("Something went wrong: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Internal Server Error");
        }
    }
}
