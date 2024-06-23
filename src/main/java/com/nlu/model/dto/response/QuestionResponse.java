package com.nlu.model.dto.response;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.nlu.model.entity.Question;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Builder
public class QuestionResponse {
	private Long id;
	private String nameQuestion;
	
	private List<OptionResponse> options;
	
	public static QuestionResponse fromEntity(Question question) {
		return QuestionResponse.builder()
				.id(question.getId())
				.nameQuestion(question.getNameQuestion())
				.options(OptionResponse.fromEntities(question.getOptions()))
				.build();
	}
	
	 public static List<QuestionResponse> fromEntities(List<Question> questions) {
	        return Optional.ofNullable(questions)
	        		.orElse(Collections.emptyList())
	        		.stream()
	                .map(QuestionResponse::fromEntity)
	                .toList();
	    }
}
