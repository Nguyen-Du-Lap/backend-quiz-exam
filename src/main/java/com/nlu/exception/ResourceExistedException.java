package com.nlu.exception;

import com.nlu.config.Translator;

public class ResourceExistedException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceExistedException(String message,Object...objects) {
		super(Translator.toLocale(message, objects));
	}

}
