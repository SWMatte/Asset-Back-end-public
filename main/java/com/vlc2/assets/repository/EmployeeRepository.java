package com.vlc2.assets.repository;

import com.vlc2.assets.dto.response.EmployeeSearchResponse;
import com.vlc2.assets.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query("""
            SELECT e
            FROM Employee AS e
            ORDER BY e.lastName asc, e.firstName asc
            """)
    List<Employee> findAllOrderByLastName();

    @Query("""
            SELECT e
            FROM Employee AS e
            WHERE CONCAT(e.firstName,' ',e.lastName) LIKE %:search% or CONCAT(e.lastName,' ',e.firstName) LIKE %:search%  or e.cf LIKE %:search% or e.address LIKE %:search%
                                  or e.birthPlace LIKE %:search% or e.company.name LIKE %:search% or e.employeeRole LIKE %:search% or e.personalPhoneNumber LIKE %:search%
                                  or e.companyPhoneNumber LIKE %:search% or e.personalEmail LIKE %:search% or e.companyEmail LIKE %:search%
            ORDER BY e.employeeRole asc, e.lastName asc , e.firstName asc , e.registrationDate asc
            """)
    List<Employee> findEmployeeLike(String search);

    @Query("""
            SELECT new com.vlc2.assets.dto.response.EmployeeSearchResponse(e.idEmployee,e.firstName,e.lastName,e.employeeRole,e.company.name)
            FROM Employee AS e
            WHERE (CONCAT(e.firstName,' ',e.lastName) LIKE %:search% or CONCAT(e.lastName,' ',e.firstName) LIKE %:search%) 
            and e.employeeRole <> 'EX_DIPENDENTE'
            ORDER BY e.lastName asc, e.firstName asc
            """)
    List<EmployeeSearchResponse> findEmployeeWithFirstNameOrLastName(String search);

}
