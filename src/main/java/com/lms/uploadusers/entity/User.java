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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Users")



public class User {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(unique = true, name = "employee_id")
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String email;
    @Enumerated(EnumType.STRING) // Assuming your enum is defined with String values
    private Roles role;
    private String password;
    private String businessUnit;
    private boolean isemailverified;
	
    
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getBusinessUnit() {
		return businessUnit;
	}
	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}
	public boolean isIsemailverified() {
		return isemailverified;
	}
	public void setIsemailverified(boolean isemailverified) {
		this.isemailverified = isemailverified;
	}
	
	public User(Long userId, Long employeeId, String firstName, String lastName, String email, Roles role,
			String password, String businessUnit,boolean isemailverified) {
		super();
		this.userId = userId;
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.role = role;
		this.password = password;
		this.businessUnit = businessUnit;
		this.isemailverified = isemailverified;
	}
	public User() {
		
	}
    
    
    
  }
