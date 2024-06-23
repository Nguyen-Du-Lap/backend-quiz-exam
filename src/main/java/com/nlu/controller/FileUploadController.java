package com.nlu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nlu.model.dto.request.QuestionRequest;
import com.nlu.model.dto.response.ResponseData;
import com.nlu.service.QuestionUploadFileService;

@RestController
@RequestMapping("/api")
public class FileUploadController {

    @Autowired
    private QuestionUploadFileService questionUploadFileService;
    @PostMapping("/upload")
    public ResponseData handleFileUpload(@RequestParam("file") MultipartFile file,
                                   @RequestParam("answerFile") MultipartFile answerFile) {

        return ResponseData.builder()
				 .status(HttpStatus.OK.value())
				 .message("List question uploaded")
				 .data(questionUploadFileService.handleFileUpload(file, answerFile))
				 .build();
    }


}
