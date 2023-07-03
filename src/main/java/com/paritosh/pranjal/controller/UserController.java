package com.paritosh.pranjal.controller;

import com.paritosh.pranjal.entity.Department;
import com.paritosh.pranjal.entity.User;
import com.paritosh.pranjal.repository.DepartmentRepository;
import com.paritosh.pranjal.repository.UserRepository;
import com.paritosh.pranjal.request.UserRequest;
import com.paritosh.pranjal.dto.UserResponse;
import com.paritosh.pranjal.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService uService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;



    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getUsers()
    {
        List<User> list = userRepository.findAll();
        List<UserResponse> responseList = new ArrayList<>();

        list.forEach(e -> {
            UserResponse userResponse = new UserResponse();
            userResponse.setId(e.getId());
            userResponse.setUserName(e.getName()); // Set the name property
            List<String> depts = new ArrayList<>();
            for(Department d : e.getDepartments())
            {
                depts.add(d.getName());
            }
            userResponse.setDepartment(depts);
            responseList.add(userResponse);
        });
        return new ResponseEntity<List<UserResponse>>(responseList,HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getEmployee(@PathVariable("id") Long id){
        return new ResponseEntity<User>(uService.getSingleUser(id),HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<String> saveEmployee(@Valid @RequestBody UserRequest userRequest)
    {
       User user = new User(userRequest);
       user = userRepository.save(user);

       for(String s: userRequest.getDepartment())
       {
           Department d = new Department();
           d.setName(s);
           d.setUser(user);

           departmentRepository.save(d);
       }
       return new ResponseEntity<String>("Record save successfully" , HttpStatus.CREATED);
    }
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateEmployee(@PathVariable Long id, @RequestBody User user) {
        User existingUser = uService.getSingleUser(id);
        if (existingUser == null) {
            return ResponseEntity.notFound().build(); // Return 404 NOT_FOUND
        }

        user.setId(id);
        User updatedUser = uService.saveUser(user);
        return ResponseEntity.ok(updatedUser); // Return 200 OK
    }


    @DeleteMapping("/users")
    public ResponseEntity<HttpStatus> deleteEmployee(@RequestParam("id") Long id){
        userRepository.deleteById(id);
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }
}
