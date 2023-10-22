package com.vlc2.assets.repository;

import com.vlc2.assets.entity.Company;
import com.vlc2.assets.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyRespository extends JpaRepository<Company, Integer> {
}
