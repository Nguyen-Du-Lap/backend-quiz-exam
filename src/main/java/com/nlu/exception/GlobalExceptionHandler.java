package com.nlu.exception;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UsernameNotFoundException.class)
	ResponseEntity<ErrorResponse> handlingUsernameNotFoundException(UsernameNotFoundException e){
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ErrorResponse(HttpStatus.NOT_FOUND.value(),e.getMessage()));
	}
	@ExceptionHandler(AuthenticationException.class)
	ResponseEntity<ErrorResponse> handlingAuthenticationException(AuthenticationException e){
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(new ErrorResponse(HttpStatus.UNAUTHORIZED.value(),e.getMessage()));
	}
	@ExceptionHandler(ResourceExistedException.class)
	ResponseEntity<ErrorResponse> handlingResourceExistedException(ResourceExistedException e){
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new ErrorResponse(HttpStatus.CONFLICT.value(),e.getMessage()));
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	ResponseEntity<ErrorResponse> handlingMethodArgumentNotValidException(MethodArgumentNotValidException e){
	        String errors = e.getBindingResult().getFieldErrors()
	            .stream()
	            .map(x -> x.getDefaultMessage())
	            .collect(Collectors.joining(", "));
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	        		.body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),errors));
		
	}
	@ExceptionHandler(NotFoundException.class)
	ResponseEntity<ErrorResponse> handlingNotFoundException(NotFoundException e){
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ErrorResponse(HttpStatus.NOT_FOUND.value(),e.getMessage()));
	}

	@ExceptionHandler(ResourceNotExistException.class)
	ResponseEntity<ErrorResponse> handlingResourceNotExistException(ResourceNotExistException e){
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ErrorResponse(HttpStatus.NOT_FOUND.value(),e.getMessage()));
	}
	
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	ResponseEntity<ErrorResponse> handlingMissingServletRequestParameterException(MissingServletRequestParameterException e){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),e.getMessage()));
	}
	@ExceptionHandler({
		MissingPathVariableException.class,
		MethodArgumentTypeMismatchException.class,
		})
	ResponseEntity<ErrorResponse> handlingMissingPathVariableException(Exception e){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),e.getMessage()));
	}
	
	@ExceptionHandler({Exception.class, RuntimeException.class})
	ResponseEntity<ErrorResponse> handlingRuntimeException(Exception e){
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage()));
	} 

}
