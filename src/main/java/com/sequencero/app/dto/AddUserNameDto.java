package com.sequencero.app.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class AddUserNameDto {
    @NotEmpty
    @Size(min = 2, max = 100, message = "enter your name")
    private String name;
}
