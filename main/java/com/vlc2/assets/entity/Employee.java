package com.vlc2.assets.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_employee")
    private int idEmployee;

    @NonNull
    @Column(name = "first_name")
    private String firstName;

    @NonNull
    @Column(name = "last_name")
    private String lastName;

    @NonNull
    @Column(name = "cf")
    private String cf;

    @Column(name = "address")
    private String address;

    @Column(name = "birth_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate birthDate;

    @Column(name = "birth_place")
    private String birthPlace;

    @NonNull
    @Column(name = "registration_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate registrationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_role")
    private EmployeeRole employeeRole;

    @Column(name = "personal_phone_number")
    private String personalPhoneNumber;

    @Column(name = "company_phone_number")
    private String companyPhoneNumber;

    @Column(name = "personal_email")
    private String personalEmail;

    @Column(name = "company_email")
    private String companyEmail;

    @NonNull
    @ManyToOne()
    @JoinColumn(name = "company")
    private Company company;

    @NonNull
    @ManyToOne()
    @JoinColumn(name = "created_by")
    private User createdBy;

}
