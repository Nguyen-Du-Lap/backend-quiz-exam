package com.nlu.exception;

import java.sql.Timestamp;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorResponse {
	private int status;
	private String message;
	private String timestamp;
	
	public ErrorResponse(int status, String message) {
		super();
		this.status = status;
		this.message = message;
		this.timestamp = new Timestamp(System.currentTimeMillis()).toString();
	}
}
