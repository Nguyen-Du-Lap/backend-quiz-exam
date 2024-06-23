package com.nlu.security;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nlu.exception.ErrorResponse;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			HttpStatus httpStatus = determineHttpStatus(e);
			sendErrorResponse(request, httpStatus, response, e);
		}

	}
	private HttpStatus determineHttpStatus(Exception e) {
	    if ( e instanceof ExpiredJwtException || e instanceof SignatureException|| e instanceof MalformedJwtException) {
	        return HttpStatus.UNAUTHORIZED;
	    }
	    return HttpStatus.BAD_REQUEST;
	}

	private void sendErrorResponse(HttpServletRequest request, HttpStatus httpStatus, HttpServletResponse response,
			Exception e) throws IOException {
		response.setStatus(httpStatus.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), e.getMessage());
		byte[] body = new ObjectMapper().writeValueAsBytes(errorResponse);

		response.getOutputStream().write(body);
		response.flushBuffer();

	}
}
