package com.nlu.model.dto.response;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.nlu.model.entity.Exam;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ExamResponse {
	private Long id;
	private String title;
	private String codeGroup;
	private String shortDescription;
	private String description;
	private int quantityQuestion;
	private List<ExamNumberResponse> examNumbers;
	private String timeExam;
	private String category;
	private Timestamp startTime;
	private Timestamp endTime;
	private boolean isPublic;

	public static ExamResponse fromEntity(Exam exam) {
		return ExamResponse.builder()
				.id(exam.getId())
				.title(exam.getTitle())
				.codeGroup(exam.getCodeGroup())
				.shortDescription(exam.getShortDescription())
				.description(exam.getDescription())
				.quantityQuestion(exam.getQuantityQuestion())
				.examNumbers(ExamNumberResponse.fromEntities(exam.getExamNumbers()))
				.timeExam(exam.getTimeExam().getName())
				.category(exam.getCategory().getName())
				.startTime(exam.getStartTime())
				.endTime(exam.getEndTime())
				.isPublic(exam.isPublic())
				.build();
	}

	public static List<ExamResponse> fromEntities(List<Exam> exams) {
		return Optional.ofNullable(exams)
				.orElse(Collections.emptyList())
				.stream()
				.map(ExamResponse::fromEntity)
				.toList();
	}

}
