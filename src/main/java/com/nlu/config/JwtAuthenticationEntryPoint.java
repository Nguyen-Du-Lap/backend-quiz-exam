package com.nlu.config;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nlu.model.dto.response.AuthenticationResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
        		.authenticated(false)
        		.error("Unauthenticated")
        		.build();

        byte[] body = new ObjectMapper().writeValueAsBytes(authenticationResponse);

        response.getOutputStream().write(body);
        response.flushBuffer();
    }
    @Override
    public void afterPropertiesSet() {
    	setRealmName("JWT Authentication");
    	super.afterPropertiesSet();
    }
    
}
