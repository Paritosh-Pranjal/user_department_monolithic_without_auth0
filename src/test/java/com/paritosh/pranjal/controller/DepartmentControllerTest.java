package com.paritosh.pranjal.controller;

import com.paritosh.pranjal.entity.Department;
import com.paritosh.pranjal.entity.User;
import com.paritosh.pranjal.repository.DepartmentRepository;
import com.paritosh.pranjal.dto.DepartmentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DepartmentControllerTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentController departmentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetDepartments() {
        // Mocking the departmentRepository
        Department department1 = new Department();
        department1.setId(1L);
        department1.setName("Department 1");

        User user1 = new User();
        user1.setId(1L);
        user1.setName("John");
        department1.setUser(user1);

        Department department2 = new Department();
        department2.setId(2L);
        department2.setName("Department 2");

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Jane");
        department2.setUser(user2);

        List<Department> departmentList = Arrays.asList(department1, department2);
        when(departmentRepository.findAll()).thenReturn(departmentList);

        // Calling the controller method
        List<DepartmentResponse> response = departmentController.getDepartments();

        // Verifying the result
        assertEquals(2, response.size());
        assertEquals("Department 1", response.get(0).getDepartmentName());
        assertEquals(1L, response.get(0).getId());
        assertEquals("John", response.get(0).getUserName());
        assertEquals("Department 2", response.get(1).getDepartmentName());
        assertEquals(2L, response.get(1).getId());
        assertEquals("Jane", response.get(1).getUserName());

        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    void testGetDepartments_EmptyList() {
        // Mocking the departmentRepository to return an empty list
        when(departmentRepository.findAll()).thenReturn(Arrays.asList());

        // Calling the controller method
        List<DepartmentResponse> response = departmentController.getDepartments();

        // Verifying the result
        assertEquals(0, response.size());

        verify(departmentRepository, times(1)).findAll();
    }
}
