package com.sequencero.app.controller;

import com.sequencero.app.dto.AddUserNameDto;
import com.sequencero.app.dto.UserCredentialsDto;
import com.sequencero.app.model.User;
import com.sequencero.app.repository.UserRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") String id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found :: " + id));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String registerUser(@RequestBody UserCredentialsDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = userRepository.insert(new User(userDto));
        return user.getId();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticateUser(@RequestBody UserCredentialsDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail());
        if (passwordEncoder.matches(userDto.getPassword(), user.getPassword()))
            return ResponseEntity.ok(user.getId());
        else
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/{id}")
    public void removeUser(@PathVariable("id") String id) {
        userRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUserName(@PathVariable("id") String id, @Valid @RequestBody AddUserNameDto userNameDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found :: " + id));

        user.setName(userNameDto.getName());
        return ResponseEntity.ok(userRepository.save(user));
    }
}