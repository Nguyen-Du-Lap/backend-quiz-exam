package com.nlu.service;

import java.util.List;

import com.nlu.model.dto.response.PageResponse;
import org.springframework.data.domain.Pageable;

import com.nlu.model.dto.response.UserResponse;

public interface UserService {
	
	UserResponse getUser(Long id);
	PageResponse<List<UserResponse>> getAllUsers(List<String> roles , Pageable pageable);

	void updateUser(Long id, Boolean active);
}
