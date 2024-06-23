package com.nlu.controller;

import java.util.List;

import com.nlu.model.dto.response.QuestionResultResponse;
import com.nlu.repository.QuestionRepository;
import com.nlu.utils.AuthenticationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nlu.model.dto.request.QuestionRequest;
import com.nlu.model.dto.response.QuestionResponse;
import com.nlu.model.dto.response.ResponseData;
import com.nlu.service.imp.QuestionService;


@RestController
@RequestMapping("/api/questions")
public class QuestionController {

	
	@Autowired private QuestionService questionService;
	
	@GetMapping("/exam-number")
	public ResponseData getQuestionByExamNumberId(@RequestParam("examNumId") Integer examNumId) {
		List<QuestionResponse> data =  questionService.getQuestionByExamNumberId(examNumId);
		return ResponseData.builder()
				 .status(HttpStatus.OK.value())
				 .message("Data questions")
				 .data(data)
				 .build();
	}
	@GetMapping("/{id}")
	public ResponseData getQuesitonById(@PathVariable Long id) {
		QuestionResponse data = questionService.getQuesitonById(id);
		return ResponseData.builder()
				 .status(HttpStatus.OK.value())
				 .message("Data question")
				 .data(data)
				 .build();
	}
	@PutMapping("/{id}")
	public ResponseData updateQuestionAndOptionRelate(@PathVariable("id") Long id,@RequestBody QuestionRequest request) {
		 questionService.updateQuestion(id,request);
		 return ResponseData.builder()
				 .status(HttpStatus.OK.value())
				 .message("Question updated successfully")
				 .build();
	}

	@GetMapping("exam-number/{examNumberId}")
	public ResponseData getQuestions(@PathVariable Integer examNumberId) {
		return ResponseData.builder()
				.status(HttpStatus.OK.value())
				.message("Question updated successfully")
				.data(questionService.getQuestionResult(examNumberId))
				.build();
	}
}
