package com.hris.HRIS.repository;

import com.hris.HRIS.model.ApplyJobModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApplyJobRepository extends MongoRepository<ApplyJobModel, String> {
}
