package com.vlc2.assets.service;

import com.vlc2.assets.dto.response.EmployeeSearchResponse;
import com.vlc2.assets.entity.Employee;

import java.util.List;

public interface EmployeeService {

    Employee save(Employee employee);

    List<Employee>  findAllOrderByLastName();

    List<Employee> findEmployeeLike(String search);

    List<EmployeeSearchResponse> findEmployeeWithFirstNameOrLastName(String search);

    Employee findById(int idEmployee);

}
