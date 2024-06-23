
package com.nlu.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.nlu.model.dto.request.ExamRequest;
import com.nlu.model.dto.response.ExamResponse;
import com.nlu.model.dto.response.PageResponse;

public interface ExamService {

	ExamResponse createExam(ExamRequest request);
	void updateExam(Long examId, ExamRequest request);
	void deleteExam(Long id);
	void updatePublicExam(Long examId,boolean request);

	PageResponse<List<ExamResponse>> getAllExams(Pageable pageable);
	ExamResponse getExamById(Long id);

	PageResponse<List<ExamResponse>>
		getExamsByCategoryAndKeyWord(String category, String keyword, Pageable pageable);
	
	PageResponse<List<ExamResponse>> getExamsByTeacherId(Long teacherId,Pageable pageable);

}