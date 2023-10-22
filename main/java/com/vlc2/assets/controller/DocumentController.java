package com.vlc2.assets.controller;

import com.vlc2.assets.aspect.Authorized;
import com.vlc2.assets.dto.response.UploadResponse;
import com.vlc2.assets.dto.request.DocumentDto;
import com.vlc2.assets.entity.History;
import com.vlc2.assets.service.DocumentService;
import com.vlc2.assets.service.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;


@Slf4j
@RestController
@RequestMapping("/document")
public class DocumentController {

    @Value("${assets.documents.folder}")
    private String assetsDocumentFolder;
    DocumentService documentService;
    HistoryService historyService;


    @Autowired
    public DocumentController(DocumentService documentService, HistoryService historyService) {
        this.documentService = documentService;
        this.historyService = historyService;
    }




    @PostMapping("/upload/{idHistory}")
    @Authorized
    public ResponseEntity<UploadResponse> uploadFile(@PathVariable int idHistory,
                                                     @RequestParam("file") MultipartFile file
    ) {
        String message = "";
        try {
            log.info("Saving file: " + file.getOriginalFilename());
            documentService.save(file, idHistory);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new UploadResponse(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getName() + ". Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new UploadResponse(message));
        }
    }

    @GetMapping("/download/assign")
    @Authorized
    public ResponseEntity<?> downloadAssignPdf(@RequestBody DocumentDto documentDto) {
        log.info("Start downloadPdf in DocumentController... with documentDto: " + documentDto);
        try {
            History history = historyService.findHistoryById(documentDto.getIdHistory());
            log.info(history.toString());
            String fileName = documentService.updateHistoryWithDocumentData(documentDto); //status_idasset_idhistory_idemployee_effectiveDate
            ByteArrayOutputStream stream = documentService.compileDocument(documentDto);
            log.info("End downloadPdf in DocumentController.");
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/pdf"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= \"" + fileName + ".pdf\"")
                    .body(new ByteArrayResource(stream.toByteArray()));
        } catch (Exception e) {
            log.error("Error downloadPdf in DocumentController: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Internal Server Error");
        }
    }

    @GetMapping("/download/dropOff")
    @Authorized
    public ResponseEntity<?> downloadDropOffPdf(@RequestBody DocumentDto documentDto) {
        log.info("Start downloadPdf in DocumentController... with documentDto: " + documentDto);
        try {

            History lastAssignedHistory = historyService.findLastAssignedHistory(documentDto.getIdEmployee(), documentDto.getIdAsset());
            String documentFile = lastAssignedHistory.getSignedDocument();
            File file = new File(System.getProperty("user.home") + assetsDocumentFolder + "/" + documentFile);
            FileInputStream fileInputStream = new FileInputStream(file);
            String fileName = documentService.updateHistoryWithDocumentData(documentDto);
            log.info("End downloadPdf in DocumentController.");
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/pdf"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename= \"" + fileName + ".pdf\"")
                    .body(new InputStreamResource(fileInputStream));
        } catch (Exception e) {
            log.error("Error downloadPdf in DocumentController: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Internal Server Error");
        }
    }
}
