package com.sequencero.app.controller;

import com.sequencero.app.model.Sequence;
import com.sequencero.app.model.User;
import com.sequencero.app.repository.SequenceRepository;
import com.sequencero.app.repository.UserRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller used for adding sequences to favourites
 */
@RestController
@RequestMapping("favourite")
public class FavouriteController {
    private final UserRepository userRepository;
    private final SequenceRepository sequenceRepository;

    public FavouriteController(SequenceRepository sequenceRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.sequenceRepository = sequenceRepository;
    }

    /**
     * Gets sequences added by the user to favourites
     *
     * @param id User's id
     * @return User's favourite sequences
     */
    @GetMapping("/{userId}")
    public List<Sequence> getFavouriteSequences(@PathVariable("userId") String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found :: " + id));
        return user.getFavourite();
    }

    /**
     * Method used for adding and removing sequences from favourites
     *
     * @param userId     - User's id
     * @param sequenceId - id of the sequence
     * @return User's favourite sequences
     */
    @PostMapping("/{userId}/{sequenceId}")
    public ResponseEntity<List<Sequence>> favouriteSequence(@PathVariable("userId") String userId, @PathVariable("sequenceId") String sequenceId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found :: " + userId));
        Sequence sequence = sequenceRepository.findById(sequenceId).orElseThrow(() -> new ResourceNotFoundException("Sequence not found :: " + sequenceId));

        List<Sequence> favourites = user.getFavourite();

        if (favourites.contains(sequence)) {
            favourites.remove(sequence);
        } else {
            favourites.add(sequence);
        }

        user.setFavourite(favourites);
        userRepository.save(user);
        return ResponseEntity.ok(favourites);
    }
}
