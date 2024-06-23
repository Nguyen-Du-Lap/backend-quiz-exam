package com.nlu.repository;

import com.nlu.model.dto.response.ExamResultResponse;
import com.nlu.service.ExamNumberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ExamNumberService examNumberService;
//    @Test
//    public void saveAnswerStudent() {
//        System.out.println(studentRepository.findById(1L));
//
//    }

    @Test
    public void testDownloadStudent() {
        studentRepository.findByCodeGroup("d444e719-2793-4c0e-8662-83cdbe929f16").forEach(System.out::println);
    }
    @Test
    public void testStudentGetIdExamNumber() {
        Long idExamNumber = studentRepository.findIdExamByIdStudent(1L);
        System.out.println(idExamNumber);
        ExamResultResponse examResultRes =
                examNumberService.getExamResultStudent(idExamNumber, 1L);
        System.out.println(examResultRes);
    }
}