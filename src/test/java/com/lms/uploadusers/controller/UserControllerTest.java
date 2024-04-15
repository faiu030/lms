package com.lms.uploadusers.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.lms.uploadusers.entity.User;
import com.lms.uploadusers.enumerate.Roles;
import com.lms.uploadusers.repo.UserRepo;
import com.lms.uploadusers.service.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

@RunWith(SpringRunner.class)
@WebMvcTest(value=UserController.class)
public class UserControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserRepo userRepo;
	
	@MockBean
	private UserService userService;
	
	@Test
	public void testupload()throws Exception{
		User mockUser=new User();
		
		mockUser.setEmpId((long) 1);
		mockUser.setEmpId((long) 12345);
		mockUser.setFirstName("Gowsic");
		mockUser.setLastName("K");
		mockUser.setEmail("gowsickandasamy410@gmail.com");
		
		mockUser.setRole(Roles.TRAINEE);
		mockUser.setPassword("GOW1234");
		mockUser.setBusinessUnit("nbu");
		
		String inputInJson = this.mapToJson(mockUser);
		
		String URL="/upload/trainee";
		
		// Assuming your UserService.saveTrainee() method returns a List<User>
		Mockito.when(userService.saveTrainee((MultipartFile) Mockito.any(User.class))).thenReturn(Collections.singletonList(mockUser));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post(URL)
				.accept(MediaType.APPLICATION_JSON).content(inputInJson)
				.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		
		String outputInJson = response.getContentAsString();
		
		assertThat(outputInJson).isEqualTo(inputInJson);
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
	}
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
	
	
}
