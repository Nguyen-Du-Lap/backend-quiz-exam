package com.nlu.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nlu.model.dto.response.ResponseData;
import com.nlu.service.ResultService;

@RestController
@RequestMapping("/api/result")
public class ResultController {

	@Autowired
	private ResultService resultService;
	
	
	@GetMapping("/students/{codeGroup}")
	public ResponseData getAllResultOfStudentByCodeGroup(@PathVariable String codeGroup) {
		Map<String,Object> workTimes = resultService.getAllResultOfStudentByCodeGroup(codeGroup);
		
		return ResponseData.builder()
				.status(HttpStatus.OK.value())
				.message(String.format("List result of group %s",codeGroup))
				.data(workTimes)
				.build();
	}
	 @GetMapping("/send-mail/{codeGroup}")
	    public ResponseData sendMailResultForStudent(@PathVariable String codeGroup) {
	        resultService.sendMailResultForStudent(codeGroup);
	        return ResponseData.builder()
	                .status(HttpStatus.OK.value())
	                .message("Emails with results have been sent successfully.")
	                .build();
	    }
	
	
	
	
}
