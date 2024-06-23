package com.nlu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nlu.model.dto.request.LoginRequest;
import com.nlu.model.dto.request.RegisterRequest;
import com.nlu.model.dto.response.AuthenticationResponse;
import com.nlu.model.dto.response.ResponseData;
import com.nlu.service.AuthService;
import com.nlu.utils.AuthenticationUtils;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired private AuthService authService;
	
	@PostMapping("/register")
	public ResponseData register(@RequestBody @Valid RegisterRequest request) {
		authService.register(request);
		return ResponseData.builder()
				.status(HttpStatus.CREATED.value())
				.message("Registered successful")
				.build();
	}
	
	@PostMapping("/login")
	public ResponseData login(@RequestBody @Valid LoginRequest request) {
		AuthenticationResponse data = authService.login(request);
		return ResponseData.builder()
				.status(HttpStatus.OK.value())
				.message("Login successful")
				.data(data)
				.build();
	}	
	
	@GetMapping("/test")
	public UserDetails getCustomUserDetails() {
		return AuthenticationUtils.extractUserDetails();
	}
}


