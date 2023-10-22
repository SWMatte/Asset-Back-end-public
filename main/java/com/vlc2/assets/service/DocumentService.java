package com.vlc2.assets.service;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.vlc2.assets.dto.response.UploadResponse;
import com.vlc2.assets.entity.AssetStatus;
import com.vlc2.assets.dto.request.DocumentDto;
import com.vlc2.assets.entity.Employee;
import com.vlc2.assets.entity.History;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

@Slf4j
@Service
public class DocumentService {


    final static String ACROFORM_DATE = "template[0].page[0].date[0]";
    final static String ACROFORM_NAME = "template[0].page[0].name[0]";
    final static String ACROFORM_LASTNAME = "template[0].page[0].lastname[0]";
    final static String ACROFORM_CF = "template[0].page[0].cf[0]";
    final static String ACROFORM_MODEL = "template[0].page[0].model[0]";
    final static String ACROFORM_BRAND = "template[0].page[0].brand[0]";
    final static String ACROFORM_SIM_NUMBER = "template[0].page[0].sim_number[0]";
    final static String PATH = "src/main/resources/template/TEMPLATE_%s_%s.pdf";
    private String dest;

    @Value("${assets.documents.folder}")
    private String assetsDocumentsFolder;

    EmployeeService employeeService;
    HistoryService historyService;


    @Autowired
    public DocumentService(EmployeeService employeeService, HistoryService historyService){
        this.employeeService = employeeService;
        this.historyService = historyService;
    }

    public ByteArrayOutputStream compileDocument(DocumentDto documentDto){
        log.info("Start compileDocument in DocumentService with documentDto: " + documentDto);
        String src = String.format(PATH, documentDto.getType().toString(), documentDto.getCompany());
                try{
                    log.info("Start employeeService.findById in DocumentService with idEmployee: " + documentDto.getIdEmployee());
                    Employee found = employeeService.findById(documentDto.getIdEmployee());
                    log.info("End employeeService.findById in DocumentService with Employee found: " + found);
                    File file = ResourceUtils.getFile(src);
                    InputStream in = new FileInputStream(file);
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    PdfReader reader = new PdfReader(in);
                    PdfStamper stamper = new PdfStamper(reader, out);
                    AcroFields form = stamper.getAcroFields();
                    form.setField(ACROFORM_DATE, String.valueOf(documentDto.getEffectiveAssignmentDate()));
                    form.setField(ACROFORM_NAME,found.getFirstName());
                    form.setField(ACROFORM_LASTNAME,found.getLastName());
                    form.setField(ACROFORM_CF,found.getCf());
                    form.setField(ACROFORM_MODEL, documentDto.getModel());
                    if(documentDto.getType().toString().equals("TELEFONO")){
                        form.setField(ACROFORM_BRAND, documentDto.getBrand());
                        form.setField(ACROFORM_SIM_NUMBER, documentDto.getSimNumber());
                    }
                    stamper.setFormFlattening(true);
                    stamper.close();
                    reader.close();
                    log.info("End compileDocument in DocumentService");
                    return out;
                } catch (DocumentException e) {
                    log.error("DocumentException, error findById in DocumentService: " + e.getMessage());
                    throw new RuntimeException("An error has occurred in a Document");
                } catch (FileNotFoundException e) {
                    log.error("FileNotFoundException, error findById in DocumentService: " + e.getMessage());
                    throw new RuntimeException("An attempt to open the file has failed");
                } catch (IOException e) {
                    log.error("IOException, error compileDocument in DocumentService: " + e.getMessage());
                    throw new RuntimeException("Failed or interrupted I/O operations");
                } catch(Exception e){
                    log.error("Error findById in DocumentService: " + e.getMessage());
                    throw new RuntimeException("Error finding employees");
                }
    }

    public String updateHistoryWithDocumentData(DocumentDto documentDto){
        try{
            log.info("Start updateHistoryWithDocumentData in DocumentService with DocumentDto: " + documentDto);
            History history = historyService.findHistoryById(documentDto.getIdHistory());
            String fileName = String.format("%s_%d_%d_%d_%s", history.getAssetStatus(), documentDto.getIdAsset(), documentDto.getIdHistory(), documentDto.getIdEmployee(), documentDto.getEffectiveAssignmentDate());
            history.setEffectiveAssignmentDate(LocalDate.parse(documentDto.getEffectiveAssignmentDate()));
            history.setSignedDocument(fileName);
            historyService.save(history);
            log.info("End updateHistoryWithDocumentData in DocumentService.");
            return fileName;
        }catch (Exception e){
            log.error("Error updateHistoryWithDocumentData in DocumentService: " + e.getMessage());
            throw new RuntimeException("Error");
        }
    }

    public void init() {
        Path path = Path.of(System.getProperty("user.home") + assetsDocumentsFolder);
        try{
            log.info("Creating the directory!");
            Files.createDirectories(path);
            log.info("Directory created at : " + path.toString() );
        }catch (IOException e){
            log.error("Can't initialize the folder: " + e.getMessage());
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }


    public UploadResponse save(MultipartFile file, int idHistory) {
        Path path = Path.of(System.getProperty("user.home") + assetsDocumentsFolder);
        String message="";
        History history = historyService.findHistoryById(idHistory);
        if(history.getAssetStatus() != AssetStatus.ASSEGNATO && history.getAssetStatus() != AssetStatus.RICONSEGNATO){
            return new UploadResponse("Sorry, it seems like you want to update the wrong history !!");
        }
        String fileName = history.getSignedDocument();
        try {
            Files.copy(file.getInputStream(), Path.of(path + "/" + fileName));
            log.info("The file has been saved!");
            message = "File uploaded successfully!";
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                log.error("A file of that name already exists! Message: " + e.getMessage());
                throw new RuntimeException("A file of that name already exists.");
            }
            log.error("Can't save the file!");
            throw new RuntimeException(e.getMessage());
        }
        history.setUploadedSignedDocument(true);
        try{
            log.info("Updating the history!");
            historyService.save(history);
        } catch (Exception e){
            log.error("Can't update the history: " + e.getMessage());
            throw new RuntimeException("Can't update the history: " + e.getMessage());
        }
        return new UploadResponse(message);
    }
}
