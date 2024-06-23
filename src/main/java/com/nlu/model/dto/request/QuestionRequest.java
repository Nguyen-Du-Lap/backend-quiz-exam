package com.nlu.model.dto.request;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.nlu.model.model.QuestionUploadFileModel;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionRequest implements Serializable{
	Long id;

	@NotBlank(message = "{question_text_blank}")
	String question;

	@NotNull(message = "{question_options_null}")
	@Size(min = 1, message = "{question_options_size}")
	List<@Valid OptionRequest> listOptionRequests;

	public static QuestionRequest toRequest(QuestionUploadFileModel model) {
		return QuestionRequest.builder()
				.question(model.getContent())
				.listOptionRequests(OptionRequest.toRequest(model))
				.build();
		
	}
	public static List<QuestionRequest> toListRequest(List<QuestionUploadFileModel> listModel) {
		return listModel.stream()
				.map(QuestionRequest::toRequest)
				.toList();
		
	}
	
}
