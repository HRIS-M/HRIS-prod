package com.hris.HRIS.repository;

import com.hris.HRIS.model.HiringManagerModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HiringManagerRepository extends MongoRepository<HiringManagerModel, String> {
}
