package com.vlc2.assets.controller;

import com.vlc2.assets.aspect.Authorized;
import com.vlc2.assets.entity.Company;
import com.vlc2.assets.entity.Employee;
import com.vlc2.assets.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/company")
public class CompanyController {

    private CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService){
        this.companyService = companyService;
    }

    @Authorized
    @GetMapping()
    public ResponseEntity<?> findAllCompany(){
        log.info("Start findAllCompany in CompanyController...");
        try{
            List<Company> found = companyService.findAllCompany();
            log.info("End findAllCompany in CompanyController");
            return ResponseEntity.ok(found);
        }catch (Exception e){
            log.error("Error findAllCompany in CompanyController: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Internal Server Error");
        }
    }

}
