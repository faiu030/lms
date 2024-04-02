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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lms.uploadusers.entity.Users;
import com.lms.uploadusers.repo.UserRepo;

@Service
public class UserService {
	@Autowired
	private UserRepo userRepo;
	
	
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

	public void saveAdmin(MultipartFile file) throws EncryptedDocumentException, IOException {
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
	    List<Users> excelDataList = rows.stream().map(row -> {
	        Users excelData = new Users();
	        int empId = (int) Double.parseDouble(row.get(0)); // Convert to double first, then cast to int
	        excelData.setEmp_id(empId);
	        excelData.setFirst_name(row.get(1));
	        excelData.setLast_name(row.get(2));
	        excelData.setEmail(row.get(3));
	        excelData.setRole("Admin");
	        String password = generatePassword();
	        excelData.setPassword(password);
	        return excelData;
	    }).collect(Collectors.toList());
	    userRepo.saveAll(excelDataList);
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
	    List<Users> excelDataList = rows.stream().map(row -> {
	        Users excelData = new Users();
	        int empId = (int) Double.parseDouble(row.get(0)); // Convert to double first, then cast to int
	        excelData.setEmp_id(empId);
	        excelData.setFirst_name(row.get(1));
	        excelData.setLast_name(row.get(2));
	        excelData.setEmail(row.get(3));
	        excelData.setRole("Trainee");
	        String password = generatePassword();
	        excelData.setPassword(password);
	        return excelData;
	    }).collect(Collectors.toList());
	    userRepo.saveAll(excelDataList);
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
