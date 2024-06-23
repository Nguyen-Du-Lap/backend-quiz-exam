package com.nlu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nlu.model.dto.request.LoginStudentRequest;
import com.nlu.model.dto.response.ResponseData;
import com.nlu.service.StudentService;
import com.nlu.utils.ExcelUtils;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/students")
public class StudentController {
	
	@Autowired
	private StudentService studentService;

	@PostMapping("/import")
	public ResponseData uploadFileExcel(@RequestParam(name = "file") MultipartFile file,
			@RequestParam(name = "code_group") String codeGroup ){
		String message = "";
		if(ExcelUtils.hasExcelFormat(file)) {
			try {
				studentService.save(file,codeGroup);
				return ResponseData.builder()
						.status(HttpStatus.CREATED.value())
						.message("upload FILE success!")
						.build();
			} catch (Exception e) {
				return ResponseData.builder()
						.status(HttpStatus.EXPECTATION_FAILED.value())
						.message("upload FILE fail!")
						.build();
			}
		}
		 message = "Please upload an excel file!";
		 return ResponseData.builder()
					.status(HttpStatus.BAD_REQUEST.value())
					.message(message)
					.build();
	}
	@GetMapping("/download/{codeGroup}")
	public ResponseEntity<ByteArrayResource> downloadExcelFileByCodeGroup(@PathVariable String codeGroup) {
		return studentService.downloadExcelFileByCodeGroup(codeGroup);
	}
	@PostMapping("/login")
	public ResponseData login(@Valid @RequestBody LoginStudentRequest request) {
		return ResponseData.builder()
				.status(HttpStatus.OK.value())
				.message("login success!")
				.data(studentService.loginByStudent(request))
				.build();
	}
	@PatchMapping("/revoke/{codeGroup}")
	public ResponseData revokeStudents(@PathVariable String codeGroup) {
		studentService.revokeStudentsByCodeGroup(codeGroup);
		return  ResponseData.builder()
				.status(HttpStatus.ACCEPTED.value())
				.message("reveoke list students success!")
				.build();
	}
}
