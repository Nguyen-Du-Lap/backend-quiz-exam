package com.nlu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nlu.model.entity.ExamNumber;
import org.springframework.data.jpa.repository.Query;

public interface ExamNumberRepository extends JpaRepository<ExamNumber, Integer> {
    //JPQL
    @Query("SELECT COUNT(q) FROM ExamNumber en JOIN en.listQuestions q WHERE en.id = ?1")
    Integer getExamNumberCountById(Long idExamNumber);

    //JPQL
    @Query("SELECT COUNT(o.id) " +
            "FROM ExamNumber en " +
            "JOIN en.listQuestions q " +
            "JOIN q.options o " +
            "JOIN UserAnswer ua ON ua.option.id = o.id " +
            "WHERE en.id = ?1 " +
            "AND ua.user.id = ?2 " +
            "AND o.correct = true")
    Integer getExamNumberQuestionCorrectByIdExamAndIdUser(Long idExamNumber, Long userId);
    //JPQL
    @Query("SELECT COUNT(o.id) " +
            "FROM ExamNumber en " +
            "JOIN en.listQuestions q " +
            "JOIN q.options o " +
            "JOIN UserAnswer ua ON ua.option.id = o.id " +
            "WHERE en.id = ?1 " +
            "AND ua.user.id = ?2 " +
            "AND o.correct = false ")
    Integer getExamNumberQuestionWrongByIdExamAndIdUser(Long idExamNumber, Long userId);

    //JPQL
    @Query("SELECT en.exam.title FROM ExamNumber en WHERE en.id = ?1 ")
    String getExamNumberExamTitleById(Long idExamNumber);
    //JPQL
    @Query("SELECT COUNT(o.id) " +
            "FROM ExamNumber en " +
            "JOIN en.listQuestions q " +
            "JOIN q.options o " +
            "JOIN UserAnswer ua ON ua.option.id = o.id " +
            "WHERE en.id = ?1 " +
            "AND ua.student.id = ?2 " +
            "AND o.correct = true")
    Integer getExamNumberQuestionCorrectByIdExamAndIdStudent(Long idExamNumber, Long idStudent);
    //JPQL
    @Query("SELECT COUNT(o.id) " +
            "FROM ExamNumber en " +
            "JOIN en.listQuestions q " +
            "JOIN q.options o " +
            "JOIN UserAnswer ua ON ua.option.id = o.id " +
            "WHERE en.id = ?1 " +
            "AND ua.student.id = ?2 " +
            "AND o.correct = false ")
    Integer getExamNumberQuestionWrongByIdExamAndIdStudent(Long idExamNumber, Long idStudent);
}
