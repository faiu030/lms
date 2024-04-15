package com.lms.uploadusers.entity;




import com.lms.uploadusers.enumerate.Roles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(unique = true, name = "emp_id")
    private Long empId;
    private String firstName;
    private String lastName;
    private String email;
    @Enumerated(EnumType.STRING) // Assuming your enum is defined with String values
    private Roles role;
    private String password;
    private String businessUnit;
  }
