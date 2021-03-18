package com.sequencero.app.service;

import com.sequencero.app.model.User;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Override
    public List<User> findAll() {

        var users = new ArrayList<User>();

        users.add(new User(1L, "Bratislava"));
        users.add(new User(2L, "Budapest"));
        users.add(new User(3L, "Prague"));
        users.add(new User(4L, "Warsaw"));
        users.add(new User(5L, "Los Angeles"));
        users.add(new User(6L, "New York"));
        users.add(new User(7L, "Edinburgh"));
        users.add(new User(8L, "Berlin"));

        return users;
    }
}

