package com.example.demo.service;

import com.example.demo.dao.DepartmentRepository;
import com.example.demo.model.Department;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    public Department saveDepartment(@NotNull Department department){
        return departmentRepository.save(department);
    }

    public List<Department> allDepartments(){
        return departmentRepository.findAll();
    }

    public Department getDepartmentById(long id){
        return departmentRepository.findById(id).get();
    }

    public void deleteDepartmentById(long id){
        departmentRepository.deleteById(id);
    }

    public Department updateDepartment(Department department, long id){
        Department dep = departmentRepository.findById(id).get();
        dep.setDepartmentName(department.getDepartmentName());
        dep.setDepartmentAddress(department.getDepartmentAddress());
        dep.setDepartmentCode(department.getDepartmentCode());
        return departmentRepository.save(dep);


    }

    public Department findDepartmentByName(String name){
        return departmentRepository.customFindDepartmentByDepartmentName(name);
    }
}
