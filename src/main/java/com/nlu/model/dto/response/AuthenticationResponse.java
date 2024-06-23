package com.nlu.model.dto.response;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticationResponse {

	private String token;
	private boolean authenticated;
	private Long userId;
	private String error;
	private Set<String> listRoles;
}
