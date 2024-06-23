package com.nlu.service;

import com.nlu.model.dto.request.LoginRequest;
import com.nlu.model.dto.request.RegisterRequest;
import com.nlu.model.dto.response.AuthenticationResponse;

public interface AuthService {
	
	AuthenticationResponse login(LoginRequest request);
	void register(RegisterRequest request);

}
