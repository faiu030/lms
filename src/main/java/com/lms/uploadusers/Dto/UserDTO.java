package com.lms.uploadusers.Dto;

import com.lms.uploadusers.enumerate.Roles;

import lombok.Data;

public class UserDTO {
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private Roles role;
    private String businessUnit;
	
	public UserDTO(Long employeeId, String firstName, String lastName, String email, Roles role, String businessUnit) {
		super();
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.role = role;
		this.businessUnit = businessUnit;
	}
	public UserDTO() {
		// TODO Auto-generated constructor stub
	}
	public Long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Roles getRole() {
		return role;
	}
	public void setRole(Roles role) {
		this.role = role;
	}
	public String getBusinessUnit() {
		return businessUnit;
	}
	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}

    // Constructors, getters, and setters
}

