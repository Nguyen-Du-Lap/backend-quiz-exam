package com.nlu.service;

public interface UserAnswerService {
    void saveAnswerUser(Long idUser, Long idOption);
    void updateAnswerUser(Long idUser, Long idOptionFirst, Long idOptionLast);

    void saveAnswerStudent(Long idStudent, Long idOption);
    void updateAnswerStudent(Long idStudent, Long idOptionFirst, Long idOptionLast);

    boolean removeAnswerUser(Integer idExamNumber, Long idUser);
}
