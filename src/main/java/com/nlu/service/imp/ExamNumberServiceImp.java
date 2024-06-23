package com.nlu.service.imp;

import com.nlu.exception.NotFoundException;
import com.nlu.exception.ResourceNotExistException;
import com.nlu.model.dto.response.ExamResponse;
import com.nlu.model.dto.response.ExamResultResponse;
import com.nlu.model.entity.Exam;
import com.nlu.model.entity.ExamNumber;
import com.nlu.model.entity.Student;
import com.nlu.model.entity.WorkTime;
import com.nlu.repository.ExamNumberRepository;
import com.nlu.repository.ExamRepository;
import com.nlu.repository.StudentRepository;
import com.nlu.repository.WorkTimeRepository;
import com.nlu.service.ExamNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ExamNumberServiceImp implements ExamNumberService {
    @Autowired
    private ExamNumberRepository examNumberRepo;
    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private WorkTimeRepository workTimeRepo;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ExamNumberRepository examNumberRepository;

    @Override
    public ExamResponse getExamNumberUser(Integer id, Long idUser) {
        Exam exam = examRepository.findByExamNumbers_Id(id);
        Set<ExamNumber>  examNumberList = exam.getExamNumbers()
                .stream()
                .filter(item -> item.getId().equals(id)).collect(Collectors.toSet());
        exam.setExamNumbers(examNumberList);
        return ExamResponse.fromEntity(exam);
    }

    @Override
    public ExamResponse getExamNumberStudent(Long idStudent) {

        // get exam by idStudent
        WorkTime workTime = workTimeRepo.findByStudent_Id(idStudent);
        ExamNumber examNumber = workTime.getExamNumber();
        Integer idExamNumber = examNumber.getId();
        Exam exam = examRepository.findByExamNumbers_Id(idExamNumber);

        Set<ExamNumber>  examNumberList = exam.getExamNumbers()
                .stream()
                .filter(item -> item.getId().equals(idExamNumber)).collect(Collectors.toSet());
        exam.setExamNumbers(examNumberList);

        // check expiry equal time between start_time and end_time
        long startTime = exam.getStartTime().getTime();
        long endTime = exam.getEndTime().getTime();
        long timeNow = new Timestamp(System.currentTimeMillis()).getTime();

        if(timeNow < startTime || timeNow > endTime) {
            return null;
        }
        return ExamResponse.fromEntity(exam);
    }

    @Override
    public ExamResultResponse getExamResultUser(Long idExamNumber, Long idUser) {
        int totalQuestion, totalCorrect, totalWrong, totalSkipped = 0;
        String examName;
        try {
            totalQuestion = examNumberRepository.getExamNumberCountById(idExamNumber);

            totalCorrect = examNumberRepository.getExamNumberQuestionCorrectByIdExamAndIdUser(idExamNumber, idUser);

            totalWrong = examNumberRepository.getExamNumberQuestionWrongByIdExamAndIdUser(idExamNumber, idUser);

            examName = examNumberRepository.getExamNumberExamTitleById(idExamNumber);
        }catch (Exception e) {
            throw new ResourceNotExistException("ExamNumber Not Found");
        }
        totalSkipped = totalQuestion - totalCorrect - totalWrong;

        return ExamResultResponse.builder()
                .examName(examName)
                .totalCorrect(totalCorrect)
                .totalWrong(totalWrong)
                .totalQuestion(totalQuestion)
                .totalSkipped(totalSkipped)
//                .totalTime(totalTime)
                .build();
    }

    @Override
    public ExamResultResponse getExamResultStudent(Long idExamNumber, Long idStudent) {
        int totalQuestion, totalCorrect, totalWrong, totalSkipped = 0;
        String examName;
        
        ExamNumber examNumber = examNumberRepository.findById(idExamNumber.intValue())
        		.orElseThrow(()-> new NotFoundException("exam_number_not_found", idExamNumber));
        Student student = studentRepository.findById(idStudent)
        		.orElseThrow(()-> new NotFoundException("student_not_found", idStudent));
        
        try {
            totalQuestion = examNumberRepository.getExamNumberCountById(idExamNumber);

            totalCorrect = examNumberRepository.getExamNumberQuestionCorrectByIdExamAndIdStudent(idExamNumber, idStudent);

            totalWrong = examNumberRepository.getExamNumberQuestionWrongByIdExamAndIdStudent(idExamNumber, idStudent);

            examName = examNumberRepository.getExamNumberExamTitleById(idExamNumber);
            
        }catch (Exception e) {
            throw new ResourceNotExistException("ExamNumber Not Found");
        }
        totalSkipped = totalQuestion - totalCorrect - totalWrong;

        return ExamResultResponse.builder()
                .examName(examName)
                .fullName(student.getFullname())
                .studentCode(student.getCode())
                .email(student.getEmail())
                .examNumberName(examNumber.getName())
                .totalCorrect(totalCorrect)
                .totalWrong(totalWrong)
                .totalQuestion(totalQuestion)
                .totalSkipped(totalSkipped)
                .build();
    }
}
