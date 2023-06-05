package com.example.demo.service;

import com.example.demo.dao.DepartmentRepository;
import com.example.demo.error.DepartmentNotFoundException;
import com.example.demo.model.Department;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Department getDepartmentById(long id) throws DepartmentNotFoundException {
        Optional<Department> department = departmentRepository.findById(id);
        if(department.isEmpty()){
            throw new DepartmentNotFoundException("Department not available");
        }
        return department.get();

    }

    public void deleteDepartmentById(long id){
        departmentRepository.deleteById(id);
    }

    public Department updateDepartment(Department department, long id) throws DepartmentNotFoundException {
        Optional<Department> OptDepartment = departmentRepository.findById(id);
        if(OptDepartment.isEmpty()){
            throw new DepartmentNotFoundException("Department not available");
        }
        Department dep = OptDepartment.get();
        dep.setDepartmentName(department.getDepartmentName());
        dep.setDepartmentAddress(department.getDepartmentAddress());
        dep.setDepartmentCode(department.getDepartmentCode());
        return departmentRepository.save(dep);


    }

    public Department findDepartmentByName(String name){
        return departmentRepository.findDepartmentByDepartmentName(name);
    }
}
