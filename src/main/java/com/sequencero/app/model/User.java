package com.sequencero.app.model;

import com.sequencero.app.dto.UserCredentialsDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("users")
public class User {
    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    private String password;

    private String name;

    @CreatedDate
    private Instant createdAt;

    private List<Sequence> favourite = new ArrayList<>();

    public User(UserCredentialsDto newUser) {
        this.email = newUser.getEmail();
        this.password = newUser.getPassword();
    }
}
