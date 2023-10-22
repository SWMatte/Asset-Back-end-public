package com.vlc2.assets.controller;

import com.vlc2.assets.aspect.Authorized;
import com.vlc2.assets.dto.response.EmployeeSearchResponse;
import com.vlc2.assets.entity.Employee;
import com.vlc2.assets.entity.User;
import com.vlc2.assets.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Authorized
    @GetMapping("/")
    public ResponseEntity<?> findAllEmployees(){
        log.info("Start findAllEmployees in EmployeeController...");
        try{
            List<Employee> found = employeeService.findAllOrderByLastName();
            log.info("End findAllEmployees in EmployeeController");
            return ResponseEntity.ok(found);
        }catch (Exception e){
            log.error("Error findAllEmployees in EmployeeController: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Internal Server Error");
        }
    }

    @Authorized
    @GetMapping("/searchAll")
    public ResponseEntity<?> findEmployeeLike(@RequestParam String search){
        log.info("Start findEmployeeLike in EmployeeController...");
        try{
            List<Employee> found = employeeService.findEmployeeLike(search);
            log.info("End findEmployeeLike in EmployeeController");
            return ResponseEntity.ok(found);
        }catch (Exception e){
            log.error("Error findEmployeeLike in EmployeeController: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Internal Server Error");
        }
    }

    @Authorized
    @GetMapping("/searchName")
    public ResponseEntity<?> findEmployeeWithFirstNameOrLastName(@RequestParam String search){
        log.info("Start findEmployeeWithFirstNameOrLastName in EmployeeController...");
        try{
            List<EmployeeSearchResponse> found = employeeService.findEmployeeWithFirstNameOrLastName(search);
            log.info("End findEmployeeWithFirstNameOrLastName in EmployeeController");
            return ResponseEntity.ok(found);
        }catch (Exception e){
            log.error("Error findEmployeeWithFirstNameOrLastName in EmployeeController: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Internal Server Error");
        }
    }

    @Authorized
    @PostMapping()
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee, User user){
        log.info("Start addEmployee in EmployeeController... with employee: " + employee + ", user: " + user);
        try{
            employee.setIdEmployee(0);
            employee.setCreatedBy(user);
            employee.setRegistrationDate(LocalDate.now());
            Employee saved = employeeService.save(employee);
            log.info("End addEmployee in EmployeeController");
            return ResponseEntity.ok(saved);
        }catch (Exception e){
            log.error("Error addEmployee in EmployeeController: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Internal Server Error");
        }
    }

    @Authorized
    @PutMapping()
    public ResponseEntity<?> updateEmployee(@RequestBody Employee employee){
        log.info("Start updateEmployee in EmployeeController... with employee: " + employee);
        try{
            Employee updated = employeeService.save(employee);
            log.info("End updateEmployee in EmployeeController");
            return ResponseEntity.ok(updated);
        }catch (Exception e){
            log.error("Error updateEmployee in EmployeeController: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Internal Server Error");
        }
    }
}
