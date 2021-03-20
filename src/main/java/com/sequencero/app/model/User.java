package com.sequencero.app.model;

import com.sequencero.app.dto.UserCredentialsDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;
    private String email;
    private String password;
    private String name;

    public User(UserCredentialsDto newUser) {
        this.email = newUser.getEmail();
        this.password = newUser.getPassword();
    }
}
