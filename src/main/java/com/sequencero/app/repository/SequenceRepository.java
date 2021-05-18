package com.sequencero.app.repository;

import com.sequencero.app.model.Sequence;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SequenceRepository extends MongoRepository<Sequence, String> {
    List<Sequence> findBySeqIsPublicIsTrue();

    List<Sequence> findByCreatedByIsLike(String userId);
}
