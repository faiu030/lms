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

import com.lms.uploadusers.entity.User;

import com.lms.uploadusers.repo.UserRepo;

@Service
public class UserService {
	@Autowired
	private UserRepo userRepo;
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

	public void saveAdmin(User newuser) {
	    User existingUser = userRepo.findByEmail(newuser.getEmail());
	    if (existingUser == null) {
	        // Create a new user
	        User adminUser = new User();
	        adminUser.setFirstName(newuser.getFirstName());
	        adminUser.setLastName(newuser.getLastName());
	        adminUser.setEmail(newuser.getEmail());
	        adminUser.setBusinessUnit(newuser.getBusinessUnit());
	        adminUser.setRole("ADMIN");
	        // Generate password
	        String password = generatePassword();
	        String generatedPassword = bcrypt.encode(password); 
	        adminUser.setPassword(generatedPassword);
	        adminUser.setEmpId(newuser.getEmpId());
	        sendEmail(newuser.getEmail(),newuser.getFirstName() ,newuser.getEmpId(), password);
	        // Save the new admin user
	        userRepo.save(adminUser);
	    } else {
	        throw new RuntimeException("User already exists");
	    }
	}


	public void saveTrainee(MultipartFile file) throws EncryptedDocumentException, IOException {
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
	        Long empId = Long.parseLong(row.get(0)); // Assuming empId is in the 1st column (index 0)
	        String email = row.get(3); // Assuming email is in the 4th column (index 3)
	        
	        // Check if employee ID already exists
	        if (userRepo.existsByEmpId(empId)) {
	            // Handle the case where employee ID already exists
	        	throw new RuntimeException("User with " +empId+" already exists");
	        	// You can throw an exception, return a message, or perform any other desired action
	        }
	        
	        // Check if email already exists
	        if (userRepo.existsByEmail(email)) {
	            // Handle the case where email already exists
	        	throw new RuntimeException("User with "+empId+" already exists");	      
	        	// You can throw an exception, return a message, or perform any other desired action
	        }
	        
	        // If both employee ID and email are unique, proceed with saving the data
	        if (!userRepo.existsByEmpId(empId) && !userRepo.existsByEmail(email)) {
	            User excelData = new User();
	            excelData.setEmpId(empId);
	            excelData.setFirstName(row.get(1));
	            excelData.setLastName(row.get(2));
	            excelData.setBusinessUnit(row.get(4));
	            excelData.setEmail(email);
	            excelData.setRole("TRAINEE");
	            // Generate password
	            String password = generatePassword();
	            String generatedPassword = bcrypt.encode(password);

	            excelData.setPassword(generatedPassword);
	            excelDataList.add(excelData);

	            // Send email with employee ID and password
	            sendEmail(email,row.get(1), empId, password);
	        }
	    }
	    try {
	        userRepo.saveAll(excelDataList);
	    } catch (DataIntegrityViolationException e) {
	        // Handle the case where there is a data integrity violation
	        // This could happen if multiple threads attempt to insert the same record simultaneously
	        System.out.println("Data integrity violation occurred.");
	        e.printStackTrace();
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
