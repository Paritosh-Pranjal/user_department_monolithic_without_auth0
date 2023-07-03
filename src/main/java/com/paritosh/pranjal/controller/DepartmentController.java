package com.paritosh.pranjal.controller;

import com.paritosh.pranjal.entity.Department;
import com.paritosh.pranjal.repository.DepartmentRepository;
import com.paritosh.pranjal.dto.DepartmentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping("/departments")
    public List<DepartmentResponse> getDepartments()
    {
        List<Department> depts = departmentRepository.findAll();
        List<DepartmentResponse> list = new ArrayList<>();
        depts.forEach(d -> {
            DepartmentResponse departmentResponse = new DepartmentResponse();
            departmentResponse.setDepartmentName(d.getName());
            departmentResponse.setId(d.getId());
            departmentResponse.setUserName(d.getUser().getName());
            list.add(departmentResponse);
        });
        return list;
    }
}
