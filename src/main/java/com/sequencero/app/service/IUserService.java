package com.sequencero.app.service;

import com.sequencero.app.model.User;
import java.util.List;

public interface IUserService {
    List<User> findAll();
}
