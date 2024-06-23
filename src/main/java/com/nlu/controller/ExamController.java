package com.nlu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.data.web.SortDefault.SortDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nlu.model.dto.request.ExamRequest;
import com.nlu.model.dto.response.ExamResponse;
import com.nlu.model.dto.response.PageResponse;
import com.nlu.model.dto.response.ResponseData;
import com.nlu.service.ExamService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/exams")
public class ExamController {

	@Autowired private ExamService examService;

	@PostMapping
	public ResponseData createExam(@Valid @RequestBody ExamRequest request) {
		ExamResponse data = examService.createExam(request);
		return ResponseData.builder()
				.status(HttpStatus.CREATED.value())
				.message("Exam created successfully")
				.data(data)
				.build();
	}

	@PutMapping("/{id}")
	public ResponseData updateExam(@PathVariable Long id, @RequestBody ExamRequest request) {
		 examService.updateExam(id, request);
		 return ResponseData.builder()
				 .status(HttpStatus.OK.value())
				 .message("Exam updated successfully")
				 .build();
	}

	@PatchMapping("/{id}")
	public ResponseData updatePublicExam(@PathVariable Long id, @RequestParam Boolean isPublic) {
		 examService.updatePublicExam(id, isPublic);
		 return ResponseData.builder()
				 .status(HttpStatus.OK.value())
				 .message("Public exam updated successfully")
				 .build();
	}


	@GetMapping
	public ResponseData getExamsByCategoryAndKeyWord(
			@RequestParam(required = false) String category,
			@RequestParam(required = false) String keyword,
			@PageableDefault(page = 0, size = 4) 
			@SortDefaults(
			@SortDefault(direction = Sort.Direction.DESC, sort = {"startTime"})
	) Pageable pageable) {
		PageResponse<List<ExamResponse>> data = examService.getExamsByCategoryAndKeyWord(category, keyword, pageable);
		 return ResponseData.builder()
				 .status(HttpStatus.OK.value())
				 .message("Exam data by category & keyword")
				 .data(data)
				 .build();
	}
	@GetMapping("/{id}")
	public ResponseData getExam(@PathVariable Long id) {
		 return ResponseData.builder()
				 .status(HttpStatus.OK.value())
				 .message("Exam data")
				 .data(examService.getExamById(id))
				 .build();
	}

	@GetMapping("/all")
	public ResponseData getExams(
			@PageableDefault(page = 0, size = 10) 
			@SortDefaults(
			@SortDefault(direction = Sort.Direction.DESC, sort = {"startTime"})
	) Pageable pageable) {
		return ResponseData.builder()
				 .status(HttpStatus.OK.value())
				 .message("Exams data")
				 .data(examService.getAllExams(pageable))
				 .build();
	}
	@DeleteMapping("/{examId}")
	public ResponseData deleteExam(@PathVariable Long examId) {
		examService.deleteExam(examId);
		return ResponseData.builder()
				 .status(HttpStatus.OK.value())
				 .message("Exams deleted successfully")
				 .build();
	}
	
	@GetMapping("/teachers/{teacherId}")
	public ResponseData getExamsByTeacherId(@PathVariable Long teacherId, 
			@PageableDefault(page = 0, size = 6) 
			@SortDefaults(
			@SortDefault(direction = Sort.Direction.DESC, sort = {"startTime"})
		) Pageable pageable) {
		return ResponseData.builder()
				 .status(HttpStatus.OK.value())
				 .message("Exams data")
				 .data(examService.getExamsByTeacherId(teacherId,pageable))
				 .build();
	}
	
}