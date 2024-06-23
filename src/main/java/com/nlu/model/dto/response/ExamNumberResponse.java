package com.nlu.model.dto.response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.nlu.model.entity.ExamNumber;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExamNumberResponse {
	private Integer id;
	private String name;
	private List<QuestionResponse> listQuestions;
    
	public static ExamNumberResponse fromEntity(ExamNumber examNumber) {
		return ExamNumberResponse.builder()
				.id(examNumber.getId())
				.name(examNumber.getName())
				.listQuestions(QuestionResponse.fromEntities(new ArrayList<>(examNumber.getListQuestions())))
				.build();
	}

	public static List<ExamNumberResponse> fromEntities(Set<ExamNumber> examNumbers) {
		return Optional.ofNullable(examNumbers).orElse(Collections.emptySet())
				.stream()
				.map(ExamNumberResponse::fromEntity)
				.toList();
	}
}
