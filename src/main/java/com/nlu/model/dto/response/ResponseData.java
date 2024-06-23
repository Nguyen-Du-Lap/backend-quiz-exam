package com.nlu.model.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ResponseData {

	private final int status;
	private final String message;
	private Object data;

	public ResponseData(int status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public ResponseData(int status, String message, Object data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
	}

}
