package com.nlu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nlu.model.dto.response.ExamResponse;
import com.nlu.model.dto.response.ExamResultResponse;
import com.nlu.model.dto.response.ResponseData;
import com.nlu.service.ExamNumberService;

@RestController
@RequestMapping("/api/exam-numbers")
public class ExamNumberController {
    @Autowired
    private ExamNumberService examNumberService;

    @GetMapping("/users/{id}")
    public ResponseData
        getExamNumberUser(@PathVariable Integer id ,@RequestParam(required = false) Long idUser) {
    	ExamResponse data = examNumberService.getExamNumberUser(id, idUser);
        return ResponseData.builder()
				 .status(HttpStatus.OK.value())
				 .message("Data exam by userId")
				 .data(data)
				 .build();
    }

    @GetMapping("/students/{idStudent}")
    public ResponseData
    getExamNumberStudent(@PathVariable Long idStudent) {
    	ExamResponse data = examNumberService.getExamNumberStudent(idStudent);
        if(data==null)
            return ResponseData.builder()
                    .status(HttpStatus.NO_CONTENT.value())
                    .message("Out side exam time!")
                    .build();
        return ResponseData.builder()
				 .status(HttpStatus.OK.value())
				 .message("Data exam by studentId")
				 .data(data)
				 .build();
    }

    @GetMapping("/users/submit/{idExamNumber}")
    public ResponseData
    getExamResultUser(@PathVariable Long idExamNumber,
                  @RequestParam Long idUser) {
    	ExamResultResponse data =examNumberService.getExamResultUser(idExamNumber, idUser);
        return ResponseData.builder()
				 .status(HttpStatus.OK.value())
				 .message("Data exam result of user")
				 .data(data)
				 .build();
    }
    @GetMapping("/students/submit/{idExamNumber}")
    public ResponseData
    getExamResultStudent(@PathVariable Long idExamNumber,
                  @RequestParam Long idStudent) {
    	ExamResultResponse data =  examNumberService.getExamResultStudent(idExamNumber, idStudent);
        
        return ResponseData.builder()
				 .status(HttpStatus.OK.value())
				 .message("Data exam result of user")
				 .data(data)
				 .build();
    }
}
