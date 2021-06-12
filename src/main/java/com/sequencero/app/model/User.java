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

    /**
     * User's email
     */
    @Indexed(unique = true)
    private String email;

    /**
     * User's password
     */
    private String password;

    /**
     * User's name
     */
    private String name;

    /**
     * Date when user was created at
     */
    @CreatedDate
    private Instant createdAt;

    /**
     * List of sequences added by user to favourites
     */
    private List<Sequence> favourite = new ArrayList<>();

    public User(UserCredentialsDto newUser) {
        this.email = newUser.getEmail();
        this.password = newUser.getPassword();
    }
}
