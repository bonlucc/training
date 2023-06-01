package com.example.demo.dao;

import com.example.demo.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    public Department findDepartmentByDepartmentName(String departmentName);

    @Query(value = "SELECT * FROM department WHERE department_name = ?1", nativeQuery = true)
    public Department customFindDepartmentByDepartmentName(String departmentName);

}
