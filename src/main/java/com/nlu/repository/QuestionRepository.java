package com.nlu.repository;

import java.util.List;

import com.nlu.model.dto.response.QuestionResultResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nlu.model.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
	List<Question> findByListExamNumbers_Id(Integer examNumberId);

	@Query(name = "Question.questionResult", nativeQuery = true)
	List<QuestionResultResponse> questionResult(@Param("examNumberId") Integer examNumberId, @Param("userId") Long userId);
}
