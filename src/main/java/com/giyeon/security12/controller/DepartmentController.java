package com.giyeon.security12.controller;

import com.giyeon.security12.domain.Department;
import com.giyeon.security12.domain.User;
import com.giyeon.security12.repository.UserRepository;
import com.giyeon.security12.service.DepartmentService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;
    private final UserRepository userRepository;

    @GetMapping("/department")
    public List<Department> getDepartment(){
        List<Department> all = departmentService.findAll();
        return all;
    }

    @PostMapping("/random")
    public String postRandom(){
        userRepository.save(new User());
        return "success";
    }
}
