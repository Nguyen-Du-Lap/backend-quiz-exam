package com.nlu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.nlu.model.dto.response.ResponseData;
import com.nlu.service.UserAnswerService;

@RestController
@RequestMapping("/api/user_answers")
public class UserAnswerController {
	@Autowired
	private UserAnswerService userAnswerService;

	@PostMapping("/users/{idUser}")
	public ResponseData saveAnswerUser(@PathVariable Long idUser, @RequestParam Long idOption) {
		userAnswerService.saveAnswerUser(idUser, idOption);
		return ResponseData.builder()
				.status(HttpStatus.OK.value())
				.message("UserAnswer saved successfully")
				.build();
	}

	@PutMapping("/users/{idUser}")
	public ResponseData updateAnswerUser(@PathVariable Long idUser, @RequestParam Long idOptionFirst,
			@RequestParam Long idOptionLast) {
		userAnswerService.updateAnswerUser(idUser, idOptionFirst, idOptionLast);
		return ResponseData.builder()
				.status(HttpStatus.OK.value())
				.message("UserAnswer updated successfully")
				.build();
	}

	@PostMapping("/students/{idStudent}")
	public ResponseData saveAnswerStudent(@PathVariable Long idStudent, @RequestParam Long idOption) {
		userAnswerService.saveAnswerStudent(idStudent, idOption);
		return ResponseData.builder()
				.status(HttpStatus.OK.value())
				.message("StudentAnswer saved successfully")
				.build();
	}

	@PutMapping("/students/{idStudent}")
	public ResponseData updateAnswerStudent(@PathVariable Long idStudent, @RequestParam Long idOptionFirst,
			@RequestParam Long idOptionLast) {
		userAnswerService.updateAnswerStudent(idStudent, idOptionFirst, idOptionLast);
		return ResponseData.builder()
				.status(HttpStatus.OK.value())
				.message("StudentAnswer updated successfully")
				.build();
	}
}
