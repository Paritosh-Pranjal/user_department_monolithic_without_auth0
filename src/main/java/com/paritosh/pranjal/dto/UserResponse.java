package com.paritosh.pranjal.dto;

import lombok.Data;

import java.util.List;
@Data
public class UserResponse {
    private Long id;
    private String userName;
    private List<String> department;
}
