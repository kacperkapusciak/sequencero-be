package com.sequencero.app.controller;

import com.sequencero.app.dto.AddSequenceDto;
import com.sequencero.app.model.Sequence;
import com.sequencero.app.repository.SequenceRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sequences")
public class SequenceController {
    private final SequenceRepository sequenceRepository;

    public SequenceController(SequenceRepository sequenceRepository) {
        this.sequenceRepository = sequenceRepository;
    }

    @GetMapping
    public List<Sequence> getSequences() {
        return sequenceRepository.findAll();
    }

    @GetMapping("/{id}")
    public Sequence getSequence(@PathVariable("id") String id) {
        return sequenceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sequence not found :: " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Sequence addSequence(@RequestBody AddSequenceDto sequenceDto) {
        return sequenceRepository.insert(new Sequence(sequenceDto));
    }

    @DeleteMapping("/{id}")
    public void removeSequence(@PathVariable("id") String id) {
        sequenceRepository.deleteById(id);
    }
}