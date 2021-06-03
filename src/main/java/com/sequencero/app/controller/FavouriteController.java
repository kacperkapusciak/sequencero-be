package com.sequencero.app.controller;

import com.sequencero.app.model.Sequence;
import com.sequencero.app.model.User;
import com.sequencero.app.repository.SequenceRepository;
import com.sequencero.app.repository.UserRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("favourite")
public class FavouriteController {
    private final UserRepository userRepository;
    private final SequenceRepository sequenceRepository;

    public FavouriteController(SequenceRepository sequenceRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.sequenceRepository = sequenceRepository;
    }

    @GetMapping("/{userId}")
    public List<Sequence> getFavouriteSequences(@PathVariable("userId") String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found :: " + id));
        return user.getFavourite();
    }

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
