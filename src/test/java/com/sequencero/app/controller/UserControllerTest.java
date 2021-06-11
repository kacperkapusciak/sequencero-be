package com.sequencero.app.controller;

import com.sequencero.app.dto.AddUserNameDto;
import com.sequencero.app.dto.GetUserDto;
import com.sequencero.app.dto.UserCredentialsDto;
import com.sequencero.app.model.User;
import com.sequencero.app.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureDataMongo
class UserControllerTest {

    @Autowired
    private UserController userController;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void addExampleUsers() {
        User user1 = new User();
        user1.setEmail("a@a.com");
        User user2 = new User();
        user2.setEmail("b@b.com");
        userRepository.saveAll(Arrays.asList(user1, user2));
    }

    @AfterEach
    void removeUsers() {
        userRepository.deleteAll();
    }

    @Test
    void getUsers_shouldReturnAllUsers() {
        List<GetUserDto> users = userController.getUsers();
        Assertions.assertEquals(userRepository.findAll().size(), users.size());
    }

    @Test
    void getUser_shouldThrowNotFoundException_whenInvalidIdIsProvided() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            userController.getUser("a");
        });
    }

    @Test
    void getUser_shouldReturnUser_whenValidIdIsProvided() {
        List<GetUserDto> users = userController.getUsers();
        GetUserDto exampleUser = users.get(0);
        String id = exampleUser.getId();

        GetUserDto user = userController.getUser(id);

        Assertions.assertEquals(exampleUser, user);
    }

    @Test
    void registerUser_shouldAddNewUser() {
        int beforeRegister = userRepository.findAll().size();
        UserCredentialsDto userToRegister = new UserCredentialsDto();
        userToRegister.setEmail("c@c.com");
        userToRegister.setPassword("password");

        userController.registerUser(userToRegister);

        int afterRegister = userRepository.findAll().size();
        Assertions.assertTrue(afterRegister > beforeRegister);
    }

    @Test
    void removeUser_shouldRemoveUser() {
        int beforeRemove = userRepository.findAll().size();
        String id = getExampleUserId();

        userController.removeUser(id);

        int afterRemove = userRepository.findAll().size();
        Assertions.assertTrue(beforeRemove > afterRemove);
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            userController.getUser(id);
        });
    }

    @Test
    void updateUserName_shouldSetName() {
        String name = "Kacper";
        String id = getExampleUserId();
        AddUserNameDto userNameDto = new AddUserNameDto();
        userNameDto.setName(name);

        ResponseEntity<GetUserDto> user = userController.updateUserName(id, userNameDto);

        Assertions.assertEquals(name, user.getBody().getName());
    }

    String getExampleUserId() {
        List<GetUserDto> users = userController.getUsers();
        GetUserDto user = users.get(0);
        return user.getId();
    }
}