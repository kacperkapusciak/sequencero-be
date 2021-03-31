package com.sequencero.app.model;

import com.sequencero.app.dto.AddSequenceDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("sequences")
public class Sequence {
    @Id
    private String id;

    private String name;

    private String[] body;

    private boolean seqIsPublic;

    @CreatedDate
    private Instant createdAt;

    private String createdBy;

    public Sequence(AddSequenceDto newSequence) {
        this.name = newSequence.getName();
        this.body = newSequence.getBody();
        this.seqIsPublic = newSequence.isSeqIsPublic();
        this.createdBy = newSequence.getCreatedBy();
    }
}
