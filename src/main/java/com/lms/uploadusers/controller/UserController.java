package com.lms.uploadusers.controller;

import java.io.IOException;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
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

	@PostMapping("/upload/admin")
	public ResponseEntity<List<List<String>>> uploadAdminExcel(@RequestParam("file") MultipartFile file)
			throws EncryptedDocumentException, IOException {
		
		userService.saveAdmin(file);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	@PostMapping("/upload/trainee")
	public ResponseEntity<List<List<String>>> uploadTraineeExcel(@RequestParam("file") MultipartFile file)
			throws EncryptedDocumentException, IOException {
		
		userService.saveTrainee(file);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}

