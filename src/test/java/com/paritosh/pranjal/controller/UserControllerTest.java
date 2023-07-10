package com.paritosh.pranjal.controller;

import com.paritosh.pranjal.entity.Department;
import com.paritosh.pranjal.entity.User;
import com.paritosh.pranjal.repository.DepartmentRepository;
import com.paritosh.pranjal.repository.UserRepository;
import com.paritosh.pranjal.request.UserRequest;
import com.paritosh.pranjal.dto.UserResponse;
import com.paritosh.pranjal.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {

//        openMocks:- ensuring that the mock objects are ready for use before each test method is executed.
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUsers() {
        // Mocking the userRepository
        User user1 = new User();
        user1.setId(1L);
        user1.setName("John");

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Jane");

        List<User> userList = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(userList);

        // Calling the controller method
        ResponseEntity<List<UserResponse>> response = userController.getUsers();

        // Verifying the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("John", response.getBody().get(0).getUserName());
        assertEquals("Jane", response.getBody().get(1).getUserName());
    }

    @Test
    void testGetSingleUser() {
        // Mocking the userService
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setName("John");
        when(userService.getSingleUser(userId)).thenReturn(user);

        // Calling the controller method
        ResponseEntity<User> response = userController.getEmployee(userId);

        // Verifying the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void testSaveUser() {
        // Mocking the userRepository and departmentRepository
        UserRequest userRequest = new UserRequest();
        userRequest.setName("John");
        userRequest.setDepartment(Arrays.asList("Department 1", "Department 2"));

        User user = new User();
        user.setId(1L);
        user.setName(userRequest.getName());

        when(userRepository.save(any(User.class))).thenReturn(user);

        // Calling the controller method
        ResponseEntity<Map<String, Object>> response = userController.saveEmployee(userRequest);

        // Verifying the result
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Record saved successfully", response.getBody().get("message"));

        verify(userRepository, times(1)).save(any(User.class));
        verify(departmentRepository, times(2)).save(any(Department.class));
    }

    @Test
    void testUpdateUser() {
        // Mocking the userService
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setName("John");

        // Mocking the userService.getSingleUser() method
        when(userService.getSingleUser(userId)).thenReturn(user);

        // Mocking the userService.saveUser() method
        when(userService.saveUser(any(User.class))).thenReturn(user);

        // Calling the controller method
        ResponseEntity<User> response = userController.updateEmployee(userId, user);

        // Verifying the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());

        // Verifying the userService method invocations
        verify(userService, times(1)).getSingleUser(userId);
        verify(userService, times(1)).saveUser(any(User.class));
    }


    @Test
    void testDeleteUser() {
        // Mocking the userRepository to return a non-empty optional
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        doNothing().when(userRepository).deleteById(userId);

        // Calling the controller method
        ResponseEntity<String> response = userController.deleteEmployee(userId);

        // Verifying the result
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }



    @Test
    void testGetUsers_EmptyList() {
        // Mocking the userRepository to return an empty list
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        // Calling the controller method
        ResponseEntity<List<UserResponse>> response = userController.getUsers();

        // Verifying the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }
    @Test
    void testSaveUser_NoDepartments() {
        // Mocking the userRepository
        UserRequest userRequest = new UserRequest();
        userRequest.setName("John");
        userRequest.setDepartment(Collections.emptyList());

        User user = new User();
        user.setId(1L);
        user.setName(userRequest.getName());

        when(userRepository.save(any(User.class))).thenReturn(user);

        // Calling the controller method
        ResponseEntity<Map<String, Object>> response = userController.saveEmployee(userRequest);

        // Verifying the result
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Record saved successfully", response.getBody().get("message"));


        verify(userRepository, times(1)).save(any(User.class));
        verify(departmentRepository, never()).save(any(Department.class));
    }


    @Test
    void testDeleteUser_NoUserFound() {
        // Mocking the userRepository to return a non-empty optional
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));

        // Calling the controller method
        ResponseEntity<String> response = userController.deleteEmployee(userId);

        // Verifying the result
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }



}
