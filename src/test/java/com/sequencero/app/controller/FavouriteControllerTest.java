package com.sequencero.app.controller;

import com.sequencero.app.model.Sequence;
import com.sequencero.app.model.User;
import com.sequencero.app.repository.SequenceRepository;
import com.sequencero.app.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureDataMongo
class FavouriteControllerTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SequenceRepository sequenceRepository;
    @Autowired
    private FavouriteController favouriteController;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setEmail("a@a.com");
        userRepository.save(user);
        Sequence seq = new Sequence();
        seq.setName("aa");
        sequenceRepository.save(seq);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        sequenceRepository.deleteAll();
    }

    @Test
    void favouriteSequence_shouldAddSequenceToUsersFavourites() {
        List<User> users = userRepository.findAll();
        String userId = users.get(0).getId();
        List<Sequence> sequences = sequenceRepository.findAll();
        String sequenceId = sequences.get(0).getId();

        ResponseEntity<List<Sequence>> favourites = favouriteController.favouriteSequence(userId, sequenceId);

        assertEquals(1, favourites.getBody().size());
    }

    @Test
    void getFavouriteSequences_shouldReturnUsersFavouriteSequences() {
        List<User> users = userRepository.findAll();
        String userId = users.get(0).getId();
        List<Sequence> sequences = sequenceRepository.findAll();
        String sequenceId = sequences.get(0).getId();
        favouriteController.favouriteSequence(userId, sequenceId);

        List<Sequence> favourites = favouriteController.getFavouriteSequences(userId);

        assertEquals(1, favourites.size());
    }
}