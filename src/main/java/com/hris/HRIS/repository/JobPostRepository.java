package com.hris.HRIS.repository;

import com.hris.HRIS.model.JobPostModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobPostRepository extends MongoRepository<JobPostModel, String> {

}
