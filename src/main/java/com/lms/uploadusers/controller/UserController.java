package com.lms.uploadusers.controller;

import java.io.IOException;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lms.uploadusers.entity.User;
import com.lms.uploadusers.service.UserService;

@RestController
@RequestMapping("/upload")
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping("/")
	public String sayHello() {
		return " Hi.. Welcome";
	}

	@PostMapping("/admin")
	public ResponseEntity<String> uploadAdminExcel(@RequestBody User newUser)
	        throws EncryptedDocumentException, IOException {
	    
	    try {
	        userService.saveAdmin(newUser);
	        return ResponseEntity.ok("Admin user uploaded successfully.");
	    } catch (RuntimeException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
	    }
	}

	@PostMapping("/trainee")
	public ResponseEntity<String> uploadTraineeExcel(@RequestParam("file") MultipartFile file)
			throws EncryptedDocumentException, IOException {
		 try {

				userService.saveTrainee(file);
		        return ResponseEntity.ok("Trainee's file uploaded successfully.");
		    } catch (RuntimeException e) {
		        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
		    }
		
	}
}

