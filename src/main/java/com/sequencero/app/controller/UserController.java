package com.sequencero.app.controller;

import com.sequencero.app.dto.AddUserNameDto;
import com.sequencero.app.dto.CreateUserDto;
import com.sequencero.app.model.User;
import com.sequencero.app.repository.UserRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody CreateUserDto newUser) {
        userRepository.insert(new User(newUser));
    }

    @DeleteMapping
    public void removeUser(String id) {
        userRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUserName(@PathVariable("id") String id, @RequestBody AddUserNameDto userNameDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found :: " + id));

        user.setName(userNameDto.getName());
        return ResponseEntity.ok(userRepository.save(user));
    }
}