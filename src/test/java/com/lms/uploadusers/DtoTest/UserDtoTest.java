package com.lms.uploadusers.DtoTest;

import com.lms.uploadusers.Dto.UserDTO;
import com.lms.uploadusers.enumerate.Roles;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDtoTest {

    @Test
    public void testConstructorAndGetters() {
        Long employeeId = 1L;
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        Roles role = Roles.ADMIN;
        String businessUnit = "Sales";

        UserDTO userDTO = new UserDTO(employeeId, firstName, lastName, email, role, businessUnit);

        assertEquals(employeeId, userDTO.getEmployeeId());
        assertEquals(firstName, userDTO.getFirstName());
        assertEquals(lastName, userDTO.getLastName());
        assertEquals(email, userDTO.getEmail());
        assertEquals(role, userDTO.getRole());
        assertEquals(businessUnit, userDTO.getBusinessUnit());
    }

    @Test
    public void testSetters() {
        UserDTO userDTO = new UserDTO();
        Long employeeId = 1L;
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        Roles role = Roles.ADMIN;
        String businessUnit = "Sales";

        userDTO.setEmployeeId(employeeId);
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setEmail(email);
        userDTO.setRole(role);
        userDTO.setBusinessUnit(businessUnit);

        assertEquals(employeeId, userDTO.getEmployeeId());
        assertEquals(firstName, userDTO.getFirstName());
        assertEquals(lastName, userDTO.getLastName());
        assertEquals(email, userDTO.getEmail());
        assertEquals(role, userDTO.getRole());
        assertEquals(businessUnit, userDTO.getBusinessUnit());
    }
}
