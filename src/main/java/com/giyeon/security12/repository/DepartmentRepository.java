package com.giyeon.security12.repository;

import com.giyeon.security12.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department,Long> {

    List<Department> getDepartmentById(Long id);

}
