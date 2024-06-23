package com.nlu.controller;

import java.util.List;

import com.nlu.model.dto.request.ExamRequest;
import com.nlu.model.dto.request.UserRequest;
import com.nlu.model.dto.response.PageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.data.web.SortDefault.SortDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.nlu.model.dto.response.ResponseData;
import com.nlu.model.dto.response.UserResponse;
import com.nlu.service.UserService;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;
	@GetMapping("/{id}")
	public ResponseData getUserInfor(@PathVariable(name = "id") Long id) {
		UserResponse data = userService.getUser(id);
		return ResponseData.builder()
				.status(HttpStatus.OK.value())
				.message("User infor")
				.data(data)
				.build();
	}
	@GetMapping
	public ResponseData getAllUsers(
			@PageableDefault(page = 0, size = 10) 
			@SortDefaults(
			@SortDefault(direction = Sort.Direction.ASC, sort = {"fullname"})
	) Pageable pageable
			) {
		PageResponse<List<UserResponse>> data = userService.getAllUsers( List.of("STUDENT", "TEACHER"), pageable);
		return ResponseData.builder()
				.status(HttpStatus.OK.value())
				.message("Users infor")
				.data(data)
				.build();
	}
	@PutMapping("/{id}")
	public ResponseData updateUser(@PathVariable Long id, @RequestParam Boolean active) {
		log.info("Updating user with id {}", id);
		log.info("Active : {}", active);
		userService.updateUser(id, active);
		return ResponseData.builder()
				.status(HttpStatus.OK.value())
				.message("Exam updated successfully")
				.build();
	}
}
