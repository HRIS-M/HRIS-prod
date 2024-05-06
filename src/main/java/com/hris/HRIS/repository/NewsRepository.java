package com.hris.HRIS.repository;

import com.hris.HRIS.model.NewsModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NewsRepository extends MongoRepository<NewsModel, String> {
}
