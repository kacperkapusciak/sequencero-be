package com.sequencero.app.repository;

import com.sequencero.app.model.Sequence;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SequenceRepository extends MongoRepository<Sequence, String> {
}
