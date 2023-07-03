package com.paritosh.pranjal.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tbl_department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    @NotBlank(message = "Name should not be null")
    private String name;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
