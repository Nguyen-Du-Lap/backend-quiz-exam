package com.nlu.config;

import java.util.Locale;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class Translator {
	private static ResourceBundleMessageSource messageSource;

    private Translator(@Autowired ResourceBundleMessageSource messageSource) {
        Translator.messageSource = messageSource;
    }

    public static String toLocale(String msgCode, Object...var2) {
        Locale locale = LocaleContextHolder.getLocale();
        String message;
    	try {
    		message = messageSource.getMessage(msgCode, null, locale);
    	} catch (NoSuchMessageException ex) {
    		message = msgCode;
    	}
        FormattingTuple formattingTuple = MessageFormatter.arrayFormat(message, var2);
        return formattingTuple.getMessage();
    }
}
