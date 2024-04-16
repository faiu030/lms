package com.lms.uploadusers.Dto;

import com.lms.uploadusers.enumerate.Roles;

import lombok.Data;
@Data
public class UserDTO {
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private Roles role;
    private String businessUnit;

    // Constructors, getters, and setters
}

