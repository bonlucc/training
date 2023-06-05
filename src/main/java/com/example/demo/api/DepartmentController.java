package com.example.demo.api;

import com.example.demo.error.DepartmentNotFoundException;
import com.example.demo.model.Department;
import com.example.demo.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    private final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    @PostMapping("/departments")
    public Department saveDepartment(@Valid @RequestBody Department department){
        LOGGER.info("Inside saveDepartment of DepartmentController");
        return departmentService.saveDepartment(department);

    }
    @GetMapping("/departments")
    public List<Department> allDepartments(){
        LOGGER.info("Inside allDepartments of DepartmentController");
        return departmentService.allDepartments();
    }

    @GetMapping("/departments/{id}")
    public Department getDepartmentById(@PathVariable("id") long id) throws DepartmentNotFoundException {
        LOGGER.info("Inside getDepartmentById of DepartmentController");
        return departmentService.getDepartmentById(id);
    }

    @DeleteMapping("/departments/{id}")
    public void deleteDepartmentById(@PathVariable("id") long id){
        LOGGER.info("Inside deleteDepartmentById of DepartmentController");
        departmentService.deleteDepartmentById(id);
    }

    @PutMapping("/departments/{id}")
    public Department updateDepartment(@RequestBody Department department, @PathVariable("id") long id) throws DepartmentNotFoundException {
        LOGGER.info("Inside updateDepartment of DepartmentController");
        return departmentService.updateDepartment(department, id);
    }

    @GetMapping("/departments/name/{name}")
    public Department getDepartmentByName(@PathVariable("name") String name){
        LOGGER.info("Inside getDepartmentByName of DepartmentController");
        return departmentService.findDepartmentByName(name);
    }
}
