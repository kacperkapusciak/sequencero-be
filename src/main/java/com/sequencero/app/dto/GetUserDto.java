package com.sequencero.app.dto;

import com.sequencero.app.model.Sequence;
import com.sequencero.app.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetUserDto {
    private String id;
    private String email;
    private String name;
    private Instant createdAt;
    private List<Sequence> favourite;

    public GetUserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.createdAt = user.getCreatedAt();
        this.favourite = user.getFavourite();
    }
}
