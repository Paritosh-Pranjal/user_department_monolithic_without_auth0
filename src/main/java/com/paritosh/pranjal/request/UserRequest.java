package com.paritosh.pranjal.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;




import java.util.List;

@Data
public class UserRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotEmpty(message = "At least one department must be provided")
    private List<@NotBlank(message = "Department name cannot be empty") String> department;


}
