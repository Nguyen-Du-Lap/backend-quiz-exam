package com.nlu.model.dto.request;

import java.io.Serializable;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterRequest implements Serializable{
	@NotBlank(message = "{username_not_blank}")
	@Length(min = 5, max = 20, message = "register_username_size}")
	private String username;
	@NotBlank(message = "{password_not_blank}")
	@Length(min = 8, max = 20, message = "{register_password_size}")
	private String password;
	@Email(regexp = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$", message = "{register_email_invalid}")
	@NotBlank(message = "{email_not_blank}")
	private String email;
	@JsonProperty("list_roles")
	private List<String> listRoles;
	
}
