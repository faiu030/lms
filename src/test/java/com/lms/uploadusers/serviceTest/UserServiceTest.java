package com.lms.uploadusers.serviceTest;


import com.lms.uploadusers.Dto.UserDTO;
import com.lms.uploadusers.entity.User;
import com.lms.uploadusers.enumerate.Roles;
import com.lms.uploadusers.exception.UserManagementException;
import com.lms.uploadusers.repo.UserRepo;
import com.lms.uploadusers.service.UserService;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAll() {
        // Stubbing the behavior of userRepo.findAll() to return a list of users
        List<User> userList = new ArrayList<>();
        // Add sample users to the list

        when(userRepo.findAll()).thenReturn(userList);

        // Call the method being tested
        List<User> result = userService.findAll();

        // Verify the result
        assertEquals(userList, result);
    }
    
    @Test
    public void testFindAll_NotEmptyList() {
        // Mock data - non-empty list of users
        List<User> userList = Arrays.asList(
            new User(1L, 1001L, "John", "Doe", "john@example.com", Roles.ADMIN, "password", "Sales",true),
            new User(2L, 1002L, "Jane", "Doe", "jane@example.com", Roles.USER, "password", "Marketing",true)
        );

        // Stubbing the behavior of userRepo.findAll() to return the mock data
        when(userRepo.findAll()).thenReturn(userList);

        // Call the method being tested
        List<User> result = userService.findAll();

        // Verify the result
        assertEquals(userList.size(), result.size());
        assertEquals(userList, result);
    }

    @Test
    public void testFindAll_EmptyList() {
        // Stubbing the behavior of userRepo.findAll() to return an empty list
        when(userRepo.findAll()).thenReturn(Collections.emptyList());

        // Call the method being tested
        List<User> result = userService.findAll();

        // Verify the result
        assertNotNull(result); // Ensure that the result is not null
        assertTrue(result.isEmpty()); // Ensure that the result is an empty list
    }

    @Test
    public void testGetUserByEmployeeId_UserFound() {
        // Mock data
        Long employeeId = 123L;
        User mockUser = new User();
        mockUser.setEmployeeId(employeeId);

        // Stubbing the behavior of userRepo.findByEmployeeId() to return the mockUser
        when(userRepo.findByEmployeeId(employeeId)).thenReturn(mockUser);

        // Call the method being tested
        User result = userService.getUserByEmployeeId(employeeId);

        // Verify the result
        assertNotNull(result); // Ensure that the result is not null
        assertEquals(mockUser, result); // Ensure that the result matches the mockUser
    }

    @Test
    public void testGetUserByEmployeeId_UserNotFound() {
        // Mock data
        Long employeeId = 123L;

        // Stubbing the behavior of userRepo.findByEmployeeId() to return null
        when(userRepo.findByEmployeeId(employeeId)).thenReturn(null);

        // Call the method being tested
        User result = userService.getUserByEmployeeId(employeeId);

        // Verify the result
        assertNull(result); // Ensure that the result is null
    }

    @Test
    public void testGetUsersByRole_NotEmptyList() {
        // Mock data - non-empty list of users
        Roles role = Roles.USER;
        List<User> userList = Arrays.asList(
            new User(1L, 1001L, "John", "Doe", "john@example.com", Roles.ADMIN, "password", "Sales",true),
            new User(2L, 1002L, "Jane", "Doe", "jane@example.com", Roles.USER, "password", "Marketing",true)
        );

        // Stubbing the behavior of userRepo.findByRole() to return the mock data
        when(userRepo.findByRole(role)).thenReturn(userList);

        // Call the method being tested
        List<User> result = userService.getUsersByRole(role);

        // Verify the result
        assertEquals(userList.size(), result.size()); // Ensure that the result has the same size as the mock data
        assertEquals(userList, result); // Ensure that the result matches the mock data
    }

    @Test
    public void testGetUsersByRole_EmptyList() {
        // Mock data - empty list of users
        Roles role = Roles.USER;

        // Stubbing the behavior of userRepo.findByRole() to return an empty list
        when(userRepo.findByRole(role)).thenReturn(Collections.emptyList());

        // Call the method being tested
        List<User> result = userService.getUsersByRole(role);

        // Verify the result
        assertNotNull(result); // Ensure that the result is not null
        assertTrue(result.isEmpty()); // Ensure that the result is an empty list
    }


    @Test
    public void testFindUserEmployeeIds_NotEmptyList() {
        // Mock data - non-empty list of users
        List<User> userList = Arrays.asList(
            new User(1L, 1001L, "John", "Doe", "john@example.com", Roles.ADMIN, "password", "Sales",true),
            new User(2L, 1002L, "Jane", "Doe", "jane@example.com", Roles.USER, "password", "Marketing",true)
        );

        // Stubbing the behavior of userRepo.findByRole() to return the mock data
        when(userRepo.findByRole(Roles.USER)).thenReturn(userList);

        // Call the method being tested
        List<Long> result = userService.findUserEmployeeIds();

        // Verify the result
        assertEquals(userList.size(), result.size()); // Ensure that the result has the same size as the mock data
        assertTrue(result.containsAll(Arrays.asList(1001L, 1002L))); // Ensure that the result contains the expected employee IDs
    }

    @Test
    public void testFindUserEmployeeIds_EmptyList() {
        // Stubbing the behavior of userRepo.findByRole() to return an empty list
        when(userRepo.findByRole(Roles.USER)).thenReturn(Collections.emptyList());

        // Call the method being tested
        List<Long> result = userService.findUserEmployeeIds();

        // Verify the result
        assertNotNull(result); // Ensure that the result is not null
        assertTrue(result.isEmpty()); // Ensure that the result is an empty list
    }

    @Test
    public void testFindEmployeeIdsByBusinessUnit_NotEmptyList() {
        // Mock data - non-empty list of users
        String businessUnit = "Sales";
        List<User> userList = Arrays.asList(
            new User(1L, 1001L, "John", "Doe", "john@example.com", Roles.ADMIN, "password", "Sales",true),
            new User(2L, 1002L, "Jane", "Doe", "jane@example.com", Roles.USER, "password", "Sales",true)
        );

        // Stubbing the behavior of userRepo.findByBusinessUnit() to return the mock data
        when(userRepo.findByBusinessUnit(businessUnit)).thenReturn(userList);

        // Call the method being tested
        List<Long> result = userService.findEmployeeIdsByBusinessUnit(businessUnit);

        // Verify the result
        assertEquals(userList.size(), result.size()); // Ensure that the result has the same size as the mock data
        assertTrue(result.containsAll(Arrays.asList(1001L, 1002L))); // Ensure that the result contains the expected employee IDs
    }

    @Test
    public void testFindEmployeeIdsByBusinessUnit_EmptyList() {
        // Mock data - empty list of users
        String businessUnit = "Finance";

        // Stubbing the behavior of userRepo.findByBusinessUnit() to return an empty list
        when(userRepo.findByBusinessUnit(businessUnit)).thenReturn(Collections.emptyList());

        // Call the method being tested
        List<Long> result = userService.findEmployeeIdsByBusinessUnit(businessUnit);

        // Verify the result
        assertNotNull(result); // Ensure that the result is not null
        assertTrue(result.isEmpty()); // Ensure that the result is an empty list
    }

    @Test
    public void testFindAllUsers_NotEmptyList() {
        // Mock data - non-empty list of users
        List<User> userList = Arrays.asList(
            new User(1L, 1001L, "John", "Doe", "john@example.com", Roles.ADMIN, "password", "Sales",true),
            new User(2L, 1002L, "Jane", "Doe", "jane@example.com", Roles.USER, "password", "Marketing",true)
        );

        // Stubbing the behavior of userRepo.findAll() to return the mock data
        when(userRepo.findAll()).thenReturn(userList);

        // Call the method being tested
        List<UserDTO> result = userService.findAllUsers();

        // Verify the result
        assertEquals(userList.size(), result.size()); // Ensure that the result has the same size as the mock data
        // You can further assert other properties of UserDTO if needed
    }

    @Test
    public void testFindAllUsers_EmptyList() {
        // Stubbing the behavior of userRepo.findAll() to return an empty list
        when(userRepo.findAll()).thenReturn(Collections.emptyList());

        // Call the method being tested
        List<UserDTO> result = userService.findAllUsers();

        // Verify the result
        assertNotNull(result); // Ensure that the result is not null
        assertTrue(result.isEmpty()); // Ensure that the result is an empty list
    }
    
    @Test
    public void testGetCellStringValue_StringType() {
        // Mock data - cell with STRING type
        Cell cell = mock(Cell.class);
        when(cell.getCellType()).thenReturn(CellType.STRING);
        when(cell.getStringCellValue()).thenReturn("test");

        // Call the method being tested
        String result = userService.getCellStringValue(cell);

        // Verify the result
        assertEquals("test", result);
    }

    @Test
    public void testGetCellStringValue_NumericType() {
        // Mock data - cell with NUMERIC type
        Cell cell = mock(Cell.class);
        when(cell.getCellType()).thenReturn(CellType.NUMERIC);
        when(cell.getNumericCellValue()).thenReturn(123.45);

        // Call the method being tested
        String result = userService.getCellStringValue(cell);

        // Verify the result
        assertEquals("123.45", result);
    }

    @Test
    public void testGetCellStringValue_BooleanType() {
        // Mock data - cell with BOOLEAN type
        Cell cell = mock(Cell.class);
        when(cell.getCellType()).thenReturn(CellType.BOOLEAN);
        when(cell.getBooleanCellValue()).thenReturn(true);

        // Call the method being tested
        String result = userService.getCellStringValue(cell);

        // Verify the result
        assertEquals("true", result);
    }

    @Test
    public void testGetCellStringValue_NullType() {
        // Mock data - cell with NULL type
        Cell cell = mock(Cell.class);
        when(cell.getCellType()).thenReturn(CellType.BLANK); // Assuming BLANK represents NULL

        // Call the method being tested
        String result = userService.getCellStringValue(cell);

        // Verify the result
        assertNull(result);
    }


    private String generateExcelContent() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Users");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Employee ID");
        headerRow.createCell(1).setCellValue("First Name");
        headerRow.createCell(2).setCellValue("Last Name");
        headerRow.createCell(3).setCellValue("Email");
        headerRow.createCell(4).setCellValue("Business Unit");

        Row dataRow = sheet.createRow(1);
        dataRow.createCell(0).setCellValue("123");
        dataRow.createCell(1).setCellValue("John");
        dataRow.createCell(2).setCellValue("Doe");
        dataRow.createCell(3).setCellValue("john@example.com");
        dataRow.createCell(4).setCellValue("Sales");

        try {
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return workbook.toString();
    }
    
    @Test
    public void testSaveUser_UserWithSameEmployeeIdExists() throws IOException, EncryptedDocumentException {
        // Mock data
        MockMultipartFile mockFile = new MockMultipartFile("file", "test.xlsx", "text/plain", generateExcelContent().getBytes());

        // Mock dependencies
        UserRepo userRepo = mock(UserRepo.class);
        UserService userService = new UserService(userRepo);

        // Stubbing the behavior of userRepo.existsByEmployeeId() to return true
        when(userRepo.existsByEmployeeId(123L)).thenReturn(true);

        // Call the method being tested and verify the exception
        assertThrows(IOException.class, () -> userService.saveUser(mockFile));
    }


    @Test
    public void testSaveUser_UserWithSameEmailExists() throws IOException, EncryptedDocumentException {
        // Mock data
        MockMultipartFile mockFile = new MockMultipartFile("file", "test.xlsx", "text/plain", generateExcelContent().getBytes());

        // Mock dependencies
        UserRepo userRepo = mock(UserRepo.class);
        UserService userService = new UserService(userRepo);

        // Stubbing the behavior of userRepo.existsByEmployeeId() to return false
        when(userRepo.existsByEmployeeId(123L)).thenReturn(false);
        // Stubbing the behavior of userRepo.existsByEmail() to return true
        when(userRepo.existsByEmail("john@example.com")).thenReturn(true);

        // Call the method being tested and verify the exception
        assertThrows(IOException.class, () -> userService.saveUser(mockFile));
    }

}

