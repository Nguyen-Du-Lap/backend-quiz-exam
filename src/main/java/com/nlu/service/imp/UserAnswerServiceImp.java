package com.nlu.service.imp;

import com.nlu.exception.ResourceNotExistException;
import com.nlu.model.entity.Option;
import com.nlu.model.entity.Student;
import com.nlu.model.entity.User;
import com.nlu.model.entity.UserAnswer;
import com.nlu.repository.OptionRepository;
import com.nlu.repository.StudentRepository;
import com.nlu.repository.UserAnswerRepository;
import com.nlu.repository.UserRepository;
import com.nlu.service.UserAnswerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserAnswerServiceImp implements UserAnswerService {
    @Autowired
    private UserAnswerRepository userAnswerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Transactional
    @Override
    public void saveAnswerUser(Long idUser, Long idOption) {
        User user =  userRepository.findById(idUser)
                .orElseThrow(() -> new ResourceNotExistException("User " + idUser + " does not exist"));
        Option option = optionRepository.findById(idOption)
                .orElseThrow(() -> new ResourceNotExistException("Option " + idOption + " does not exist"));
        UserAnswer userAnswer = UserAnswer.builder()
                .user(user)
                .option(option)
                .student(null)
                .build();

        userAnswerRepository.save(userAnswer);
    }

    @Transactional
    @Override
    public void updateAnswerUser(Long idUser, Long idOptionFirst, Long idOptionLast) {
        // find the answer old
        UserAnswer userAnswer = userAnswerRepository
                .findByUser_IdAndOption_Id(idUser, idOptionFirst);
        // find the option new
        Option option = optionRepository.findById(idOptionLast)
                .orElseThrow(() -> new ResourceNotExistException("Option " + idOptionFirst + " does not exist"));
        userAnswer.setOption(option);
        userAnswerRepository.save(userAnswer);
    }
    @Transactional
    @Override
    public void saveAnswerStudent(Long idStudent, Long idOption) {
        Student student =  studentRepository.findById(idStudent)
                .orElseThrow(() -> new ResourceNotExistException("Student " + idStudent + " does not exist"));
        Option option = optionRepository.findById(idOption)
                .orElseThrow(() -> new ResourceNotExistException("Option " + idOption + " does not exist"));
        UserAnswer userAnswer = UserAnswer.builder()
                .user(null)
                .option(option)
                .student(student)
                .build();
        userAnswerRepository.save(userAnswer);
    }

    @Transactional
    @Override
    public void updateAnswerStudent(Long idStudent, Long idOptionFirst, Long idOptionLast) {
        // find the answer old
        UserAnswer userAnswer = userAnswerRepository
                .findByStudent_IdAndOption_Id(idStudent, idOptionFirst)
                .orElseThrow(() -> new ResourceNotExistException("Option " + idOptionFirst + " does not exist"));
        // find the option new
        Option option = optionRepository.findById(idOptionLast)
                .orElseThrow(() -> new ResourceNotExistException("Option " + idOptionFirst + " does not exist"));
        userAnswer.setOption(option);
        userAnswerRepository.save(userAnswer);
    }
    @Transactional
    @Override
    public boolean removeAnswerUser(Integer idExamNumber, Long idUser) {
        List<Long> listIdOption = userAnswerRepository.getIdOptionByIdExamNumberAndIdUser(idExamNumber, idUser);
        if (!listIdOption.isEmpty()) {
            for (Long idOption : listIdOption) {
               userAnswerRepository.deleteByOptionIdAndUserId(idOption, idUser);
            }
            return true;
        }
        return false;
    }
}
