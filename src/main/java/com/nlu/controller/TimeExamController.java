package com.nlu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nlu.model.dto.response.ResponseData;
import com.nlu.service.TimeExamService;

@RestController
@RequestMapping("/api/time-exams")
public class TimeExamController {

	@Autowired
	private TimeExamService timeExamService;
	
	@GetMapping
	public ResponseData getAllTimeExam() {
		return ResponseData.builder()
				.status(200)
				.message("Time exam data")
				.data(timeExamService.getAllTimeExam())
				.build();
	}
}
