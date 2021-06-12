package com.sequencero.app.controller;

import com.sequencero.app.dto.AddUserNameDto;
import com.sequencero.app.dto.GetUserDto;
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
import java.util.stream.Collectors;

/**
 * Controller used for managing users
 */
@RestController
@RequestMapping("users")
public class UserController {
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Gets users
     *
     * @return List of users
     */
    @GetMapping
    public List<GetUserDto> getUsers() {
        List<User> users = userRepository.findAll();
        // convert List<User> to List<GetUserDto>
        return users.stream().map(GetUserDto::new).collect(Collectors.toList());
    }

    /**
     * Gets user by id
     *
     * @param id User's id
     * @return User
     */
    @GetMapping("/{id}")
    public GetUserDto getUser(@PathVariable("id") String id) {
        return new GetUserDto(userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found :: " + id)));
    }

    /**
     * Registers a new user
     *
     * @param userDto User credentials (email & password) passed by the client
     * @return Authorization token
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String registerUser(@RequestBody UserCredentialsDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = userRepository.insert(new User(userDto));
        return user.getId();
    }

    /**
     * Authenticates the user when correct credentials provided
     *
     * @param userDto User credentials (email & password) passed by the client
     * @return Authorization token
     */
    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticateUser(@RequestBody UserCredentialsDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail());
        if (passwordEncoder.matches(userDto.getPassword(), user.getPassword()))
            return ResponseEntity.ok(user.getId());
        else
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

    /**
     * Removes user by given id
     *
     * @param id User's id
     */
    @DeleteMapping("/{id}")
    public void removeUser(@PathVariable("id") String id) {
        userRepository.deleteById(id);
    }

    /**
     * Updates user's name
     *
     * @param id          User's id
     * @param userNameDto Name of the user
     * @return User
     */
    @PutMapping("/{id}")
    public ResponseEntity<GetUserDto> updateUserName(@PathVariable("id") String id, @Valid @RequestBody AddUserNameDto userNameDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found :: " + id));

        user.setName(userNameDto.getName());
        return ResponseEntity.ok(new GetUserDto(userRepository.save(user)));
    }
}