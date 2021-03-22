package com.sequencero.app.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserCredentialsDto {
    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 5, max = 100, message = "password has to be longer than 5 characters")
    private String password;
}
