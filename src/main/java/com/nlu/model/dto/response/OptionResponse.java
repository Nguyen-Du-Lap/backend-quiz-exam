package com.nlu.model.dto.response;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.nlu.model.entity.Option;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OptionResponse {
	private Long id;
	private String nameOption;
	private Boolean correct;
	public static OptionResponse fromEntity(Option option) {
		return OptionResponse.builder()
				.id(option.getId())
				.nameOption(option.getNameOption())
				.correct(option.getCorrect())
				.build();
	}

	public static List<OptionResponse> fromEntities(Set<Option> options) {
		return Optional.ofNullable(options)
				.orElse(Collections.emptySet())
				.stream()
				.map(OptionResponse::fromEntity)
				.toList();
	}
}
