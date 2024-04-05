package com.lms.uploadusers.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.dao.DataIntegrityViolationException;

import com.lms.uploadusers.entity.Roles;
import com.lms.uploadusers.entity.User;
import com.lms.uploadusers.exception.UserManagementException;
import com.lms.uploadusers.repo.RolesRepo;
import com.lms.uploadusers.repo.UserRepo;

@Service
public class UserService {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private RolesRepo rolesRepo;
	@Autowired
	private JavaMailSender emailSender;
	BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
	public static String generatePassword() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();

        // Add first three letters from the alphabet
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            char letter = alphabet.charAt(random.nextInt(alphabet.length()));
            sb.append(letter);
        }

        // Add three random digits
        for (int i = 0; i < 3; i++) {
            int digit = random.nextInt(10); // 0 to 9
            sb.append(digit);
        }

        return sb.toString();
    }



	// Other imports...

	public void saveTrainee(MultipartFile file) throws EncryptedDocumentException, IOException {
	    try {
	        List<List<String>> rows = new ArrayList<>();

	        Workbook workbook = WorkbookFactory.create(file.getInputStream());
	        Sheet sheet = workbook.getSheetAt(0); // Change index to 0
	        rows = StreamSupport.stream(sheet.spliterator(), false)
	                .skip(1)
	                .map(row -> StreamSupport
	                        .stream(row.spliterator(), false)
	                        .map(this::getCellStringValue)
	                        .collect(Collectors.toList()))
	                .collect(Collectors.toList());
	        System.out.println("rows :: " + rows);
	        // Save data to the database
	        List<User> excelDataList = new ArrayList<>();
	        for (List<String> row : rows) {
	        	double empIdDouble = Double.parseDouble(row.get(0)); // Assuming empId is in the 1st column (index 0)
	            long empId = (long) empIdDouble;	            
	            String email = row.get(3); // Assuming email is in the 4th column (index 3)

	            // Check if employee ID already exists
	            if (userRepo.existsByEmpId(empId)) {
	                throw new UserManagementException("User with empId " + empId + " already exists");
	            }

	            // Check if email already exists
	            if (userRepo.existsByEmail(email)) {
	                throw new UserManagementException("User with email " + email + " already exists");
	            }

	            // If both employee ID and email are unique, proceed with saving the data
	            User excelData = new User();
	            excelData.setEmpId(empId);
	            excelData.setFirstName(row.get(1));
	            excelData.setLastName(row.get(2));
	            excelData.setBusinessUnit(row.get(4));
	            excelData.setEmail(email);
	            
	            // Fetching the trainee role
	            Roles traineeRole = rolesRepo.findRolesByroleName("Trainee");
	            if (traineeRole == null) {
	                throw new UserManagementException("Trainee role not found"); // Handle if role not found
	            }
	            excelData.setRole(traineeRole);

	            // Generate password
	            String password = generatePassword();
	            String generatedPassword = bcrypt.encode(password);

	            excelData.setPassword(generatedPassword);
	            excelDataList.add(excelData);

	            // Send email with employee ID and password
	            sendEmail(email, row.get(1), empId, password);
	        }

	        userRepo.saveAll(excelDataList);
	    } catch (EncryptedDocumentException | IOException e) {
            throw new RuntimeException("Error processing Excel file: " + e.getMessage());
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Data integrity violation occurred: " + e.getMessage());
        } catch (UserManagementException e) {
            throw new RuntimeException("User management exception occurred: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage());
        }
	}


	 private void sendEmail(String to,String firstName, Long empId, String password) {
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setFrom("THBS");
	        message.setTo(to);
	        message.setSubject("Welcome to the LMS");
	        message.setText("Hi "+firstName+" \n\nWelcome to the LMS!\n\nYour Employee ID: " + empId +
	                "\nYour Password: " + password);

	        emailSender.send(message);
	    }
	private String getCellStringValue(Cell cell) {
		CellType cellType = cell.getCellType();

		if (cellType == CellType.STRING) {
			return cell.getStringCellValue();
		} else if (cellType == CellType.NUMERIC) {
			return String.valueOf(cell.getNumericCellValue());
		} else if (cellType == CellType.BOOLEAN) {
			return String.valueOf(cell.getBooleanCellValue());
		}

		return null;
	}
	
}
