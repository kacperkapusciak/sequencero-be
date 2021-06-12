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

    /**
     * Sequence name
     */
    private String name;

    /**
     * Sequence body
     */
    private String[] body;

    /**
     * Used for marking the sequence as public or private
     */
    private boolean seqIsPublic;

    /**
     * Date when sequence was created at
     */
    @CreatedDate
    private Instant createdAt;

    /**
     * Id of the user who created this sequence
     */
    private String createdBy;

    public Sequence(AddSequenceDto newSequence) {
        String content = newSequence.getBody();
        this.body = content.split("\\r?\\n");
        this.name = newSequence.getName();
        this.seqIsPublic = newSequence.isSeqIsPublic();
        this.createdBy = newSequence.getCreatedBy();
    }
}
