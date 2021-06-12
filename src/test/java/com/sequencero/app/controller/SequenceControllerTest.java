package com.sequencero.app.controller;

import com.sequencero.app.dto.AddSequenceDto;
import com.sequencero.app.model.Sequence;
import com.sequencero.app.repository.SequenceRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureDataMongo
class SequenceControllerTest {

    @Autowired
    private SequenceController sequenceController;
    @Autowired
    private SequenceRepository sequenceRepository;

    @BeforeEach
    void setUp() {
        Sequence seq1 = new Sequence();
        seq1.setName("aaa");
        seq1.setSeqIsPublic(true);
        seq1.setCreatedBy("a");
        Sequence seq2 = new Sequence();
        seq2.setName("bbb");
        seq2.setSeqIsPublic(false);
        Sequence seq3 = new Sequence();
        seq3.setName("aab");
        seq3.setSeqIsPublic(true);
        sequenceRepository.saveAll(Arrays.asList(seq1, seq2, seq3));
    }

    @AfterEach
    void tearDown() {
        sequenceRepository.deleteAll();
    }

    @Test
    void getSequences_shouldReturnPublicSequences_whenQisEmpty() {
        List<Sequence> sequences = sequenceController.getSequences("");
        assertEquals(2, sequences.size());
    }

    @Test
    void getSequences_shouldReturnSequencesLikeName_whenQisProvided() {
        List<Sequence> sequences = sequenceController.getSequences("aaa");
        assertEquals(1, sequences.size());
    }

    @Test
    void getSequence_shouldReturnSequence_whenValidIdIsProvided() {
        List<Sequence> sequences = sequenceController.getSequences("");
        Sequence sequence = sequences.get(0);
        String id = sequence.getId();

        Sequence sequence1 = sequenceController.getSequence(id);

        assertEquals(sequence, sequence1);
    }

    @Test
    void getSequence_shouldThrowNotFoundException_whenInvalidIdIsProvided() {
        assertThrows(ResourceNotFoundException.class, () -> {
            sequenceController.getSequence("a");
        });
    }

    @Test
    void getUserSequences_shouldReturnSequencesCreatedByUser() {
        List<Sequence> createdByUser = sequenceController.getUserSequences("a");
        assertEquals(1, createdByUser.size());
    }

    @Test
    void addSequence_shouldAddNewSequence() {
        int beforeAdd = sequenceRepository.findAll().size();
        AddSequenceDto sequenceDto = new AddSequenceDto();
        sequenceDto.setName("name");
        sequenceDto.setBody("body");
        sequenceDto.setSeqIsPublic(true);
        sequenceDto.setCreatedBy("b");
        sequenceController.addSequence(sequenceDto);
        int afterAdd = sequenceRepository.findAll().size();
        assertTrue(afterAdd > beforeAdd);
    }

    @Test
    void editSequence_shouldEditSequence() {
        AddSequenceDto sequenceDto = new AddSequenceDto();
        sequenceDto.setName("name");
        sequenceDto.setBody("body");
        sequenceDto.setSeqIsPublic(true);
        sequenceDto.setCreatedBy("b");
        Sequence sequence = sequenceController.addSequence(sequenceDto);

        AddSequenceDto sequenceDto2 = new AddSequenceDto();
        sequenceDto2.setName("differentName");
        sequenceDto2.setBody("differentBody");
        sequenceDto2.setSeqIsPublic(true);
        sequenceDto2.setCreatedBy("b");

        ResponseEntity<Sequence> editedSeq = sequenceController.editSequence(sequence.getId(), sequenceDto2);
        assertEquals(sequenceDto2.getName(), editedSeq.getBody().getName());
        assertEquals(sequenceDto2.getBody(), editedSeq.getBody().getBody()[0]);
        assertEquals(sequenceDto2.isSeqIsPublic(), editedSeq.getBody().isSeqIsPublic());
        assertEquals(sequenceDto2.getCreatedBy(), editedSeq.getBody().getCreatedBy());

    }

    @Test
    void removeSequence_shouldRemoveSequence() {
        int beforeRemove = sequenceRepository.findAll().size();
        List<Sequence> sequences = sequenceController.getSequences("");
        Sequence sequence = sequences.get(0);
        String id = sequence.getId();

        sequenceController.removeSequence(id);

        int afterRemove = sequenceRepository.findAll().size();
        assertTrue(beforeRemove > afterRemove);
    }
}