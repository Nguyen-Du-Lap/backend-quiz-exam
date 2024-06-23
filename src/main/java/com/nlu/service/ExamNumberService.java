package com.nlu.service;

import com.nlu.model.dto.response.ExamResponse;
import com.nlu.model.dto.response.ExamResultResponse;

public interface ExamNumberService {
    ExamResponse getExamNumberUser(Integer id, Long idUser);
    ExamResponse getExamNumberStudent(Long idStudent);
    ExamResultResponse getExamResultUser(Long idExamNumber, Long idUser);
    ExamResultResponse getExamResultStudent(Long idExamNumber, Long idStudent);
}
