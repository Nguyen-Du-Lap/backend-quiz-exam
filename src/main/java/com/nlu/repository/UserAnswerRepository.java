package com.nlu.repository;

import com.nlu.model.entity.UserAnswer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {

    UserAnswer findByUser_IdAndOption_Id(Long idUser, Long idOption);

    Optional<UserAnswer> findByStudent_IdAndOption_Id(Long idStudent, Long idOptionFirst);

    // JPQL
    @Query("SELECT ua.option.id " +
            "FROM ExamNumber en " +
            "JOIN en.listQuestions q " +
            "JOIN q.options o " +
            "JOIN UserAnswer ua ON ua.option.id = o.id " +
            "WHERE en.id = ?1 " +
            "AND ua.user.id = ?2 ")
    List<Long> getIdOptionByIdExamNumberAndIdUser(Integer idExamNumber, Long idUser);

    @Modifying
    @Transactional
    @Query("DELETE FROM UserAnswer ua WHERE ua.option.id = ?1 AND ua.user.id = ?2")
    void deleteByOptionIdAndUserId( Long optionId, Long userId);
}
