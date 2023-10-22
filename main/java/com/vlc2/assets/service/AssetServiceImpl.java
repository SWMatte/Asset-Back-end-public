package com.vlc2.assets.service;


import com.vlc2.assets.dto.request.AssetsCreateRequest;
import com.vlc2.assets.entity.*;
import com.vlc2.assets.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AssetServiceImpl implements AssetService {

    @Autowired
    AssetRepository assetRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    HistoryRepository historyRepository;

    @Override
    public void add(AssetsCreateRequest element, User user) {
        try {
            log.info("Enter into - add method in AssetService");
            if (element != null) {
                Company company = companyRepository.findById(element.getIdCompany()).orElseThrow();
                Asset asset = element.getAsset();
                asset.setCreatedBy(user);
                asset.setCompany(company);

                Optional<Employee> employee = employeeRepository.findById(element.getIdEmployee());

                assetRepository.save(asset);

                if (!element.isCreateHistory()) {
                    History history;
                    if (employee.isPresent()) {
                        history = History.builder()
                                .asset(asset)
                                .assetStatus(element.getStatus())
                                .assignmentDate(LocalDate.now())
                                .createdBy(user)
                                .employee(employee.get())
                                .build();
                    } else {

                        history = History.builder()
                                .asset(asset)
                                .assetStatus(element.getStatus())
                                .assignmentDate(LocalDate.now())
                                .createdBy(user)
                                .build();

                    }
                    historyRepository.save(history);
                }

            }
        } catch (IllegalArgumentException e) {
            log.error("Error adding Asset: " + e.getMessage());
        }
    }



}
