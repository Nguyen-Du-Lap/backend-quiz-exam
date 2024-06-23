package com.nlu.exception;

import com.nlu.config.Translator;

public class ResourceNotExistException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ResourceNotExistException(String message,Object...objects) {
		super(Translator.toLocale(message, objects));
	}
}
