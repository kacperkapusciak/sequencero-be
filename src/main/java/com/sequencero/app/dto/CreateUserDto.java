package com.sequencero.app.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class CreateUserDto {
    @Id
    private String id;
    private String email;
    private String password;
}
