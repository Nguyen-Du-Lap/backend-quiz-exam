package com.nlu.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserRequest {
    @NotBlank(message = "{username_not_blank}")
    private String username;
    @Email(message = "{email_not_blank}")
    private String email;
    private String fullname;
    @NotNull(message = "{exam_is_public_null}")
    private Boolean active;
}
