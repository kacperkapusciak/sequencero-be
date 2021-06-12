package com.sequencero.app.controller;

import com.sequencero.app.dto.AddSequenceDto;
import com.sequencero.app.model.Sequence;
import com.sequencero.app.repository.SequenceRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller used for managing sequences
 */
@RestController
@RequestMapping("sequences")
public class SequenceController {
    private final SequenceRepository sequenceRepository;

    public SequenceController(SequenceRepository sequenceRepository) {
        this.sequenceRepository = sequenceRepository;
    }

    /**
     * Gets sequences marked as public
     *
     * @param q Parameter used for searching by sequence name. Defaults to empty string
     * @return List of public sequences
     */
    @GetMapping
    public List<Sequence> getSequences(@RequestParam(defaultValue = "") String q) {
        if (q.isEmpty()) {
            return sequenceRepository.findBySeqIsPublicIsTrue();
        }

        return sequenceRepository.findBySeqIsPublicIsTrueAndNameIgnoreCaseLike(q);
    }

    /**
     * Gets sequence by id
     *
     * @param id Sequence's id
     * @return Sequence
     */
    @GetMapping("/{id}")
    public Sequence getSequence(@PathVariable("id") String id) {
        return sequenceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sequence not found :: " + id));
    }

    /**
     * Gets sequences added by particular user
     *
     * @param userId User's id
     * @return Lis of sequences add by user of given id
     */
    @GetMapping("/user/{id}")
    public List<Sequence> getUserSequences(@PathVariable("id") String userId) {
        return sequenceRepository.findByCreatedByIsLike(userId);
    }

    /**
     * Adds new sequence
     *
     * @param sequenceDto Sequence passed by the client
     * @return Sequence
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Sequence addSequence(@RequestBody AddSequenceDto sequenceDto) {
        return sequenceRepository.insert(new Sequence(sequenceDto));
    }

    /**
     * Edits sequence by given id
     *
     * @param id          Sequence's id
     * @param sequenceDto Sequence passed by the client
     * @return Edited sequence
     */
    @PutMapping("/{id}")
    public ResponseEntity<Sequence> editSequence(@PathVariable("id") String id, @RequestBody AddSequenceDto sequenceDto) {
        Sequence sequence = sequenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sequence not found :: " + id));

        sequence.setName(sequenceDto.getName());
        String content = sequenceDto.getBody();
        sequence.setBody(content.split("\\r?\\n"));
        sequence.setSeqIsPublic(sequenceDto.isSeqIsPublic());

        return ResponseEntity.ok(sequenceRepository.save(sequence));
    }

    /**
     * Removes sequence by given id
     *
     * @param id Sequence's id
     */
    @DeleteMapping("/{id}")
    public void removeSequence(@PathVariable("id") String id) {
        sequenceRepository.deleteById(id);
    }
}
