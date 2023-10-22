package com.vlc2.assets.service;

import com.vlc2.assets.dto.response.EmployeeSearchResponse;
import com.vlc2.assets.entity.Employee;
import com.vlc2.assets.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService{

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public Employee save(Employee employee) {
        log.info("Start save in EmployeeServiceImpl... with employee: " + employee);
        try{
            Employee saved = employeeRepository.save(employee);
            log.info("End save in EmployeeServiceImpl");
            return saved;
        }catch(Exception e){
            log.error("Error save in EmployeeServiceImpl: " + e.getMessage());
            throw new RuntimeException("Error saving the employee", e);
        }
    }

    @Override
    public List<Employee> findAllOrderByLastName() {
        log.info("Start findAllOrderByLastName in EmployeeServiceImpl...");
        try{
            List<Employee> found = employeeRepository.findAllOrderByLastName();
            log.info("End findAllOrderByLastName in EmployeeServiceImpl");
            return found;
        }catch(Exception e){
            log.error("Error findAllOrderByLastName in EmployeeServiceImpl: " + e.getMessage());
            throw new RuntimeException("Error finding employees", e);
        }
    }

    @Override
    public List<Employee> findEmployeeLike(String search) {
        log.info("Start findEmployeeLike in EmployeeServiceImpl...");
        try{
            List<Employee> found = employeeRepository.findEmployeeLike(search);
            log.info("End findEmployeeLike in EmployeeServiceImpl");
            return found;
        }catch(Exception e){
            log.error("Error findEmployeeLike in EmployeeServiceImpl: " + e.getMessage());
            throw new RuntimeException("Error finding employees", e);
        }
    }

    @Override
    public List<EmployeeSearchResponse> findEmployeeWithFirstNameOrLastName(String search) {
        log.info("Start findEmployeeWithFirstNameOrLastName in EmployeeServiceImpl...");
        try{
            List<EmployeeSearchResponse> found = employeeRepository.findEmployeeWithFirstNameOrLastName(search);
            log.info("End findEmployeeWithFirstNameOrLastName in EmployeeServiceImpl");
            return found;
        }catch(Exception e){
            log.error("Error findEmployeeWithFirstNameOrLastName in EmployeeServiceImpl: " + e.getMessage());
            throw new RuntimeException("Error finding employees", e);
        }
    }

    @Override
    public Employee findById(int idEmployee) {
        log.info("Start findEmployeeWithFirstNameOrLastName in EmployeeServiceImpl...");
        try{
            Optional<Employee> found = employeeRepository.findById(idEmployee);
            return found.orElseThrow();
        }catch(Exception e){
            log.error("Error findEmployeeWithFirstNameOrLastName in EmployeeServiceImpl: " + e.getMessage());
            throw new RuntimeException("Error finding employees", e);
        }
    }
}
