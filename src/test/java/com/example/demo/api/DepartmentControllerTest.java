package com.example.demo.api;

import com.example.demo.error.DepartmentNotFoundException;
import com.example.demo.model.Department;
import com.example.demo.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;
    private Department department;
    @BeforeEach
    void setUp() {
        department = Department.builder()
                .departmentId(1L)
                .departmentCode("IT-06")
                .departmentAddress("Ahmedabad")
                .departmentName("IT")
                .build();
    }

    @Test
    void saveDepartment() throws Exception {
        Department inputDepartment = Department.builder()
                .departmentId(1L)
                .departmentCode("IT-06")
                .departmentAddress("Ahmedabad")
                .departmentName("IT")
                .build();
        Mockito.when(departmentService.saveDepartment(inputDepartment)).thenReturn(department);
        mockMvc.perform(post("/departments")
                .contentType(MediaType.APPLICATION_JSON).content("{\n" +
                        "    \"departmentName\": \"IT\",\n" +
                        "    \"departmentId\": \"1\",\n" +
                        "    \"departmentCode\": \"IT-06\",\n" +
                        "    \"departmentAddress\": \"Ahmedabad\"\n" +
                        "}")).andExpect(status().isOk());
    }

    @Test
    void getDepartmentById() throws Exception {
        Mockito.when(departmentService.getDepartmentById(1L)).thenReturn(department);

        mockMvc.perform(get("/department/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());//.andExpect(jsonPath("$.departmentName").value(department.getDepartmentName()));
    }
}