package com.sequencero.app.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class User {
    @Id
    private final String id;
    private final String email;
    private final String password;
//    private final String name;
}
