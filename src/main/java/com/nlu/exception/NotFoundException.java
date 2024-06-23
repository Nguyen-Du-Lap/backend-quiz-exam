package com.nlu.exception;

import com.nlu.config.Translator;

public class NotFoundException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotFoundException(String message,Object...objects) {
		super(Translator.toLocale(message,objects));
	}
}
