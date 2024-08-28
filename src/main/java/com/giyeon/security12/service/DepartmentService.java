package com.giyeon.security12.service;

import com.giyeon.security12.domain.Department;
import com.giyeon.security12.repository.DepartmentRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public List<Department> findAll() {
        List<Department> departmentById = departmentRepository.findAll();

        return departmentById;
    }

}
