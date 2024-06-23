package com.nlu.model.dto.request;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginRequest implements Serializable{
	
	@NotBlank(message = "{username_not_blank}")
	private String username;
	@NotBlank(message = "{password_not_blank}")
	private String password;
}
