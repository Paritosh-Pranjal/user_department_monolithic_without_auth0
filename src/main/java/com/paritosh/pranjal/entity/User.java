package com.paritosh.pranjal.entity;

import com.paritosh.pranjal.request.UserRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
@Table(name="tbl_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    @NotBlank(message = "Name should not be null")
    private String name;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Department> departments;

    public User(UserRequest req)
    {
        this.name = req.getName();
    }
    public User() {
        departments = new ArrayList<>();
    }
    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
