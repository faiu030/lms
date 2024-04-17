package com.lms.uploadusers.entityTest;

import com.lms.uploadusers.entity.User;
import com.lms.uploadusers.enumerate.Roles;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    @Test
    public void testConstructorAndGetters() {
        Long userId = 1L;
        Long employeeId = 1001L;
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        Roles role = Roles.ADMIN;
        String password = "password";
        String businessUnit = "Sales";
        boolean isemailverified = true;
        
        User user = new User(userId, employeeId, firstName, lastName, email, role, password, businessUnit,isemailverified);

        assertEquals(userId, user.getUserId());
        assertEquals(employeeId, user.getEmployeeId());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(email, user.getEmail());
        assertEquals(role, user.getRole());
        assertEquals(password, user.getPassword());
        assertEquals(businessUnit, user.getBusinessUnit());
        assertEquals(isemailverified, user.isIsemailverified());
    }

    @Test
    public void testSetters() {
        User user = new User();
        Long userId = 1L;
        Long employeeId = 1001L;
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        Roles role = Roles.ADMIN;
        String password = "password";
        String businessUnit = "Sales";
        boolean isemailverified = true;
        
        user.setUserId(userId);
        user.setEmployeeId(employeeId);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setRole(role);
        user.setPassword(password);
        user.setBusinessUnit(businessUnit);
        user.setIsemailverified(true);
        assertEquals(userId, user.getUserId());
        assertEquals(employeeId, user.getEmployeeId());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(email, user.getEmail());
        assertEquals(role, user.getRole());
        assertEquals(password, user.getPassword());
        assertEquals(businessUnit, user.getBusinessUnit());
        assertEquals(isemailverified, user.isIsemailverified());
    }
}
