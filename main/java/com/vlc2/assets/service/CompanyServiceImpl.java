package com.vlc2.assets.service;

import com.vlc2.assets.entity.Company;
import com.vlc2.assets.entity.Employee;
import com.vlc2.assets.repository.CompanyRespository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CompanyServiceImpl implements CompanyService{

    private CompanyRespository companyRespository;

    @Autowired
    public CompanyServiceImpl(CompanyRespository companyRespository){
        this.companyRespository = companyRespository;
    }

    @Override
    public List<Company> findAllCompany() {
        log.info("Start findAllCompany in CompanyServiceImpl...");
        try{
            List<Company> found = companyRespository.findAll();
            log.info("End findAllCompany in CompanyServiceImpl");
            return found;
        }catch(Exception e){
            log.error("Error findAllCompany in CompanyServiceImpl: " + e.getMessage());
            throw new RuntimeException("Error finding companies", e);
        }
    }
}
