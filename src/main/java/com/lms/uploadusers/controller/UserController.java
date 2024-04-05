package com.lms.uploadusers.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


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

	
	@PostMapping("/trainee")
	public ResponseEntity<String> uploadTraineeExcel(@RequestParam("file") MultipartFile file) {
	    try {
	        userService.saveTrainee(file);
	        return ResponseEntity.ok("Trainee's file uploaded successfully.");
	    } catch (IOException e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: IOException occurred while processing the file.");
	    } catch (RuntimeException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
	    }
	}

}

