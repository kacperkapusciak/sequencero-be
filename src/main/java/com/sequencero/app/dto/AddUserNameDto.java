package com.sequencero.app.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Data transfer object used for updating user's name
 */
@Data
public class AddUserNameDto {
    @NotEmpty
    @Size(min = 2, max = 100, message = "enter your name")
    private String name;
}
