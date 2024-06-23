package com.nlu.service.imp;

import java.util.Arrays;
import java.util.List;

import com.nlu.model.dto.response.ExamResponse;
import com.nlu.model.dto.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.nlu.model.dto.response.UserResponse;
import com.nlu.model.entity.User;
import com.nlu.repository.UserRepository;
import com.nlu.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

	private final UserRepository userRepository;

	@PostAuthorize("hasRole('ADMIN') || returnObject.code == authentication.name")
	@Override
	public UserResponse getUser(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("user not found"));
		return UserResponse.fromEntity(user);

	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@Override
	public PageResponse<List<UserResponse>> getAllUsers(List<String> roles , Pageable pageable) {
		Page<User> pageUser = userRepository.findByListRoles_NameIn(roles, pageable);
		return PageResponse.<List<UserResponse>>builder()
				.content(UserResponse.fromEntities(pageUser.getContent()))
				.totalPage(pageUser.getTotalPages())
				.totalElement(pageUser.getTotalElements())
				.size(pageUser.getSize())
				.currentPage(pageUser.getPageable().getPageNumber())
				.build();
	}

	@Override
	public void updateUser(Long id, Boolean active) {
		User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("user not found"));
		user.setActive(active);
		userRepository.save(user);
	}

}
