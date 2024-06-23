package com.nlu.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginStudentRequest {
	
	@Email(message = "register_email_invalid")
	@NotBlank(message = "email_not_blank")
	private String email;
	@NotBlank(message = "password_not_blank")
	@Size(min = 5, max = 20, message = "register_password_size")
	private String password;
	@NotBlank(message = "code_group_not_blank")
	private String codeGroup;
}
